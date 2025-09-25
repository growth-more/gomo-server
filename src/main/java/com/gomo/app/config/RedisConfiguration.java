package com.gomo.app.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

	@Value("${spring.data.redis.host}")
	private String HOST;

	@Value("${spring.data.redis.port}")
	private int PORT;

	@Value("${spring.data.redis.password}")
	private String PASSWORD;

	private LettuceConnectionFactory createConnectionFactory(int databaseIndex) {
		RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(HOST, PORT);
		redisConfig.setDatabase(databaseIndex);
		redisConfig.setPassword(PASSWORD);
		return new LettuceConnectionFactory(redisConfig);
	}

	@Bean
	@Primary
	LettuceConnectionFactory redisConnectionFactory() {
		return createConnectionFactory(0);
	}

	@Bean
	@Qualifier("authCode")
	LettuceConnectionFactory redisConnectionFactoryEmail() {
		return createConnectionFactory(1);
	}

	@Bean
	@Qualifier("session")
	LettuceConnectionFactory redisConnectionFactorySession() {
		return createConnectionFactory(2);
	}

	@Bean
	@Qualifier("authCodeRedisTemplate")
	public RedisTemplate<String, Object> authCodeRedisTemplate(@Qualifier("authCode") RedisConnectionFactory emailAuthRedisConnectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(emailAuthRedisConnectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new StringRedisSerializer());
		return template;
	}

	@Bean
	@Primary
	@Qualifier("jwtSessionRedisTemplate")
	public RedisTemplate<String, Object> jwtSessionRedisTemplate(@Qualifier("session") RedisConnectionFactory jwtSessionRedisConnectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(jwtSessionRedisConnectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new StringRedisSerializer());
		return template;
	}
}
