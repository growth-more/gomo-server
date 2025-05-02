package com.gomo.app.member.infrastructure.repository;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

// TODO <jhl221123> to <nurdy>: 레디스 접근하는 로직이라면 Repository 가 더 적합하지 않을까요?
@Repository
@RequiredArgsConstructor
public class EmailAuthRedisRepository {

	@Qualifier("emailAuthRedisTemplate")
	private final RedisTemplate<String, Object> emailAuthRedisTemplate;
	private static final int TIME_OUT = 300; // 5min

	public void setAuthCode(String email, String authCode) {
		emailAuthRedisTemplate.opsForValue().set(email, authCode, TIME_OUT, TimeUnit.SECONDS);
	}

	public String getAuthCode(String email) {
		return (String)emailAuthRedisTemplate.opsForValue().get(email);
	}

	public void deleteAuthCode(String email) {
		emailAuthRedisTemplate.delete(email);
	}
}
