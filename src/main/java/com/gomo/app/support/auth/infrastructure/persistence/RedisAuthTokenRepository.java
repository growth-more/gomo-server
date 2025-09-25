package com.gomo.app.support.auth.infrastructure.persistence;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.gomo.app.support.auth.domain.repository.AuthTokenRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RedisAuthTokenRepository implements AuthTokenRepository {

	@Qualifier("jwtSessionRedisTemplate")
	private final RedisTemplate<String, Object> redisTemplate;

	@Value("${jwt.expiration.refresh}")
	private long REFRESH_EXP_TIME;

	@Override
	public void setRefreshToken(UUID principalId, String refreshToken) {
		redisTemplate.opsForValue().set(principalId.toString(), refreshToken, REFRESH_EXP_TIME, TimeUnit.SECONDS);
	}

	@Override
	public String getRefreshToken(UUID principalId) {
		return (String)redisTemplate.opsForValue().get(principalId.toString());
	}

	@Override
	public void deleteRefreshToken(UUID principalId) {
		redisTemplate.delete(principalId.toString());
	}
}
