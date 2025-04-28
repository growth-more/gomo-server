package com.gomo.app.common.util;

import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

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


    public String generateAccessToken(UUID memberId){
        return generateToken(memberId, accessExpirationTime);
    }

    public String generateRefreshToken(UUID memberId){
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

    public long extractExpirationTime(String token){
        Claims claims = parseClaims(token);
        Date expiration = claims.getExpiration();
        return expiration.getTime() / 1000;
    }

    private String generateToken(UUID memberId, long expirationTime){
        Date exp = new Date(System.currentTimeMillis() + expirationTime);

        return Jwts.builder()
                .subject(memberId.toString())
                .issuedAt(new Date())
                .expiration(exp)
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
