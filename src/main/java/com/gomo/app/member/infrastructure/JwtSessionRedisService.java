package com.gomo.app.member.infrastructure;

import com.gomo.app.member.domain.model.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class JwtSessionRedisService {

    @Qualifier("jwtSessionRedisTemplate")
    private final RedisTemplate<String, Object> jwtSessionRedisTemplate;

    @Value("${jwt.expiration.refresh}")
    private long EXP_TIME;

    public void setRefreshToken(MemberId memberId, String refreshToken){
        jwtSessionRedisTemplate.opsForValue().set(memberId.toString(), refreshToken, EXP_TIME, TimeUnit.MILLISECONDS);
    }

    public String getRefreshToken(MemberId memberId){
        return (String) jwtSessionRedisTemplate.opsForValue().get(memberId.toString());
    }

    public void deleteRefreshToken(MemberId memberId){
        jwtSessionRedisTemplate.delete(memberId.toString());
    }
}
