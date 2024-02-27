package com.cbzf.apis.user.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cbzf.apis.user.repository.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {

    private static final String SECRET_KEY = "YourSecretKey"; // Replace with your secret key
    private static final long EXPIRATION_TIME = 86400000; // 24 hours in milliseconds

    public String generateToken(UserEntity user) {
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + EXPIRATION_TIME);

        return JWT.create()
                .withSubject(user.getEmail())
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withClaim("role", user.getRole())
                .withClaim("userId", user.getIdUser())
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }
}
