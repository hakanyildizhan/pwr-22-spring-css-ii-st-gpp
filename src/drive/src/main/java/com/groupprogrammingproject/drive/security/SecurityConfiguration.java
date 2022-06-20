package com.groupprogrammingproject.drive.security;

import com.groupprogrammingproject.drive.authentication.service.UserDetailsServiceWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;

import javax.servlet.http.HttpServletRequest;

import static com.groupprogrammingproject.drive.account.controller.AccountController.ACCOUNTS;
import static com.groupprogrammingproject.drive.account.controller.AccountController.ACCOUNT_DETAILS_URL;
import static com.groupprogrammingproject.drive.security.Role.USER;
import static org.springframework.http.HttpMethod.*;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    public static final String FILES_ENDPOINT = "/files";
    private final UserDetailsServiceWrapper userDetailsService;

    @Value("${drive.secret}")
    private String secret;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().configurationSource(SecurityConfiguration::corsConfiguration)
                .and()
                .headers(
                        httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer
                                .xssProtection()
                                .and()
                                .contentSecurityPolicy("script-src 'self'")
                )
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(ACCOUNT_DETAILS_URL).hasRole(USER)
                .antMatchers(POST, ACCOUNTS).permitAll()
                .antMatchers(FILES_ENDPOINT + "/**").authenticated()
                .and()
                .addFilter(new AuthorizationFilter(authenticationManager(), userDetailsService, secret))
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
    }

    private static CorsConfiguration corsConfiguration(HttpServletRequest request) {
        CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
        config.addAllowedMethod(PUT);
        config.addAllowedMethod(DELETE);
        return config;
    }
}
