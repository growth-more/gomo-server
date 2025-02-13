package com.gomo.app.member.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class EmailAuthRedisService {

    @Qualifier("emailAuthRedisTemplate")
    private final RedisTemplate<String, Object> emailAuthRedisTemplate;
    private static final int TIME_OUT = 300; // 5min

    public void setAuthCode(String email, String authCode){
        emailAuthRedisTemplate.opsForValue().set(email, authCode, TIME_OUT, TimeUnit.SECONDS);
    }

    public String getAuthCode(String email){
        System.out.println("@EmailAuthService#getAuthCode");
        System.out.println(email);
        System.out.println(emailAuthRedisTemplate.opsForValue().get(email));
        return (String) emailAuthRedisTemplate.opsForValue().get(email);
    }

    public void deleteAuthCode(String email){
        emailAuthRedisTemplate.delete(email);
    }
}
