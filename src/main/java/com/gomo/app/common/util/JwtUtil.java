package com.gomo.app.common.util;

import com.gomo.app.member.domain.model.MemberId;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final long accessExpirationTime;
    private final long refreshExpirationTime;
    private final SecretKey secretKey;

    public JwtUtil(@Value("${jwt.expiration.access}") long accessExpirationTime, @Value("${jwt.expiration.refresh}") long refreshExpirationTime) {
        this.accessExpirationTime = accessExpirationTime;
        this.refreshExpirationTime = refreshExpirationTime;
        this.secretKey = Jwts.SIG.HS256.key().build();
    }


    public String generateAccessToken(MemberId memberId){
        return generateToken(memberId, accessExpirationTime);
    }

    public String generateRefreshToken(MemberId memberId){
        return generateToken(memberId, refreshExpirationTime);
    }

    public String extractMemberId(String token){
        return parseClaims(token).getSubject();
    }

    public boolean validateToken(String token){
        try{
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }

    private String generateToken(MemberId memberId, long expirationTime){
        return Jwts.builder()
                .subject(memberId.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(this.secretKey)
                .compact();
    }

    private Claims parseClaims(String token){
        return Jwts.parser()
                .verifyWith(this.secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
