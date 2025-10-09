package com.gomo.app.test.container;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class RedisContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	public static final GenericContainer<?> redisContainer;

	static {
		redisContainer = new GenericContainer<>("redis:7.0.8-alpine")
			.withExposedPorts(6379)
			.withReuse(true)
			.waitingFor(Wait.forListeningPort());
		redisContainer.start();
	}

	@Override
	public void initialize(ConfigurableApplicationContext context) {
		TestPropertyValues.of(
			"spring.data.redis.host=" + redisContainer.getHost(),
			"spring.data.redis.port=" + redisContainer.getMappedPort(6379),
			"spring.data.redis.password=gomo123@"
		).applyTo(context.getEnvironment());
	}
}
