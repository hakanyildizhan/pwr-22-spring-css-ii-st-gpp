package com.groupprogrammingproject.drive.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.groupprogrammingproject.drive.authentication.service.UserDetailsServiceWrapper;
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

import static java.util.Objects.nonNull;

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
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
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
