package com.groupprogrammingproject.drive.authentication.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TokenFactoryTest {

    private static final String EMAIL = "email@email.com";

    private static final String SECRET = "secret";

    private final TokenFactory tokenFactory = new TokenFactory();

    @Test
    void shouldCreateNewTokenForEmailAndSecret() {
        String token = tokenFactory.generateToken(EMAIL, SECRET);

        DecodedJWT decodedJWT = JWT.decode(token);

        assertThat(decodedJWT.getSubject()).isEqualTo(EMAIL);
        assertThat(decodedJWT.getIssuedAt()).isInThePast();
    }

}