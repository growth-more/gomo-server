package com.gomo.app.auth.infrastructure.repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.gomo.app.auth.domain.repository.EmailAuthCodeRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RedisEmailAuthRedisRepository implements EmailAuthCodeRepository {

	@Qualifier("emailAuthRedisTemplate")
	private final RedisTemplate<String, Object> redisTemplate;

	private final long TIME_OUT = 300L;

	@Override
	public void save(String email, String authCode) {
		redisTemplate.opsForValue().set(email, authCode, TIME_OUT, TimeUnit.SECONDS);
	}

	@Override
	public Optional<String> findByEmail(String email) {
		return Optional.ofNullable((String)redisTemplate.opsForValue().get(email));
	}

	@Override
	public void delete(String email) {
		redisTemplate.delete(email);
	}
}
