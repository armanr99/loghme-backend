package com.loghme.models.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JWTService {
    private static final String SECRET = "loghmeloghmeloghmeloghmeloghmeloghme";
    private static final String ISSUER = "loghme.com";
    private static final long EXPIRATION_TIME_MS = 24 * 60 * 60 * 1000;
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    private static JWTService instance = null;

    public static JWTService getInstance() {
        if (instance == null) {
            instance = new JWTService();
        }
        return instance;
    }

    String createToken(int userId) {
        Date issuedAt = new Date();
        Date expirationTime = new Date(issuedAt.getTime() + EXPIRATION_TIME_MS);
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setIssuedAt(issuedAt)
                .setExpiration(expirationTime)
                .setIssuer(ISSUER)
                .setSubject(Integer.toString(userId))
                .signWith(key, SIGNATURE_ALGORITHM)
                .compact();
    }
}
