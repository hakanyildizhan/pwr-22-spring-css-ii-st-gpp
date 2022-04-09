package com.groupprogrammingproject.drive.authentication.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
public class TokenFactory {

    public String generateToken(String email, String secret) {
        return JWT.create()
                .withIssuedAt(Date.from(Instant.now()))
                .withSubject(email)
                .withExpiresAt(Date.from(Instant.now().plus(60, MINUTES)))
                .sign(Algorithm.HMAC512(secret));
    }
}
