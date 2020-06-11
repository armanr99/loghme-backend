package com.loghme.models.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;
import com.loghme.controllers.DTOs.responses.Google.GoogleResponse;
import com.loghme.utils.HTTPRequester;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;

import static com.loghme.configs.Configs.GOOGLE_TOKEN_URL;

public class JWTService {
    private Gson gson;
    private static final String SECRET = "loghmeloghmeloghmeloghmeloghmeloghme";
    private static final String ISSUER = "loghme.com";
    private static final long EXPIRATION_TIME_MS = 24 * 60 * 60 * 1000;
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    private static final SecretKey JWT_KEY =
            Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    private static final String CLIENT_ID =
            "941686033847-nojkfumil3h3p0hv978kbgcrr39htjsq.apps.googleusercontent.com";

    private static JWTService instance = null;
    private static GoogleIdTokenVerifier googleVerifier;

    public static JWTService getInstance() {
        if (instance == null) {
            instance = new JWTService();
        }
        return instance;
    }

    private JWTService() {
        googleVerifier =
                new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                        .setAudience(Collections.singletonList(CLIENT_ID))
                        .build();
        gson = new Gson();
    }

    String createToken(int userId) {
        Date issuedAt = new Date();
        Date expirationTime = new Date(issuedAt.getTime() + EXPIRATION_TIME_MS);

        return Jwts.builder()
                .setIssuedAt(issuedAt)
                .setExpiration(expirationTime)
                .setIssuer(ISSUER)
                .setSubject(Integer.toString(userId))
                .signWith(JWT_KEY, SIGNATURE_ALGORITHM)
                .compact();
    }

    public String getSubject(String token) {
        try {
            Jws<Claims> jws =
                    Jwts.parserBuilder().setSigningKey(JWT_KEY).build().parseClaimsJws(token);
            return jws.getBody().getSubject();
        } catch (Exception ex) {
            return null;
        }
    }

    String getGoogleEmail(String googleToken) {
        try {
            String tokenUrl = GOOGLE_TOKEN_URL + googleToken;
            String googleResponseStr = HTTPRequester.get(tokenUrl);
            if (googleResponseStr == null) return null;
            else {
                GoogleResponse googleResponse = gson.fromJson(googleResponseStr, GoogleResponse.class);
                return googleResponse.getEmail();
            }
        } catch (Exception ex) {
            return null;
        }
    }

    String getGoogleEmailWithVerifier(String googleToken) {
        try {
            GoogleIdToken idToken = googleVerifier.verify(googleToken);
            if (idToken == null) return null;
            else {
                GoogleIdToken.Payload payload = idToken.getPayload();
                return payload.getEmail();
            }
        } catch (Exception ex) {
            return null;
        }
    }
}
