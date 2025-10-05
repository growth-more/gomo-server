package com.gomo.app.common;

import org.flywaydb.core.Flyway;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import com.zaxxer.hikari.HikariDataSource;

// todo jhl221123: 자원별 컨테이너 초기화 로직을 분리해야 한다. 설정 파일을 개별로 초기화 가능한지 파악하는 것이 최우선이다.
public class DatabaseContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	public static MySQLContainer<?> mysqlContainer;
	public static final GenericContainer<?> redisContainer;
	public static final RabbitMQContainer rabbitMQContainer;

	static {
		mysqlContainer = new MySQLContainer<>("mysql:8.0.28")
			.withDatabaseName("gomo")
			.withUsername("gomo")
			.withPassword("1111")
			.withReuse(true)
			.waitingFor(Wait.forListeningPort());
		mysqlContainer.start();

		HikariDataSource ds = new HikariDataSource();
		ds.setJdbcUrl(mysqlContainer.getJdbcUrl());
		ds.setUsername(mysqlContainer.getUsername());
		ds.setPassword(mysqlContainer.getPassword());

		Flyway flyway = Flyway.configure()
			.dataSource(ds)
			.locations("classpath:db/migration")
			.load();
		flyway.migrate();

		redisContainer = new GenericContainer<>("redis:7.0.8-alpine")
			.withExposedPorts(6379)
			.withReuse(true)
			.waitingFor(Wait.forListeningPort());
		redisContainer.start();

		rabbitMQContainer = new RabbitMQContainer("rabbitmq:3.13-management")
			.withExposedPorts(5672)
			.withReuse(true)
			.waitingFor(Wait.forListeningPort());
		rabbitMQContainer.start();
	}

	@Override
	public void initialize(ConfigurableApplicationContext context) {
		TestPropertyValues.of(
			"spring.datasource.url=" + mysqlContainer.getJdbcUrl(),
			"spring.datasource.username=" + mysqlContainer.getUsername(),
			"spring.datasource.password=" + mysqlContainer.getPassword(),
			"spring.data.redis.host=" + redisContainer.getHost(),
			"spring.data.redis.port=" + redisContainer.getMappedPort(6379),
			"spring.rabbitmq.host=" + rabbitMQContainer.getHost(),
			"spring.rabbitmq.port=" + rabbitMQContainer.getMappedPort(5672),
			"spring.rabbitmq.username=" + rabbitMQContainer.getAdminUsername(),
			"spring.rabbitmq.password=" + rabbitMQContainer.getAdminPassword(),
			"spring.rabbitmq.publisher-confirm-type=correlated"
		).applyTo(context.getEnvironment());
	}
}
