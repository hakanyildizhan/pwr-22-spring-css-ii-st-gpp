package com.groupprogrammingproject.drive.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupprogrammingproject.drive.authentication.service.UserDetailsServiceWrapper;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

@Slf4j
public class AuthorizationFilter extends BasicAuthenticationFilter {

    private static final String HEADER = "Authorization";
    private static final String PREFIX = "Bearer ";
    private final UserDetailsServiceWrapper userDetailsService;

    private final String secret;

    public AuthorizationFilter(AuthenticationManager authenticationManager,
                               UserDetailsServiceWrapper userDetailsService,
                               String secret) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
        this.secret = secret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = null;

        try {
            authentication = getAuthentication(request);
        } catch (Exception e) {
            log.error("Error while validating token: {}", e.getMessage());
            String error = "";
            if (e.getClass().equals(TokenExpiredException.class)) {
                error = "Token expired";
            } else if (e.getClass().equals(SignatureVerificationException.class)) {
                error = "Invalid token";
            } else {
                error = "Internal error";
            }

            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("message", error);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getWriter(), errorDetails);
            filterChain.doFilter(request, response);
            return;
        }

        if (authentication == null) {
            filterChain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER);
        if (token != null && token.startsWith(PREFIX)) {
            DecodedJWT decodedJwt = JWT.require(Algorithm.HMAC512(secret))
                .build()
                .verify(token.replace(PREFIX, ""));
            String email = decodedJwt.getSubject();
            Date expirationTime = decodedJwt.getExpiresAt();
            if (email != null && expirationTime.after(new Date())) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                if (nonNull(userDetails) && userDetails.isEnabled()) {
                    return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                }
            }
        }
        return null;
    }


}
