package com.gomo.app.member.infrastructure;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtSessionRedisService {

    @Qualifier("jwtSessionRedisTemplate")
    private final RedisTemplate<String, Object> jwtSessionRedisTemplate;

    @Value("${jwt.expiration.refresh}")
    private long EXP_TIME;

    public void setRefreshToken(UUID memberId, String refreshToken){
        jwtSessionRedisTemplate.opsForValue().set(memberId.toString(), refreshToken, EXP_TIME, TimeUnit.MILLISECONDS);
    }

    public String getRefreshToken(UUID memberId){
        return (String) jwtSessionRedisTemplate.opsForValue().get(memberId.toString());
    }

    public void updateRefreshToken(UUID memberId, String refreshToken){
        setRefreshToken(memberId, refreshToken);
    }

    public void deleteRefreshToken(UUID memberId){
        jwtSessionRedisTemplate.delete(memberId.toString());
    }
}
