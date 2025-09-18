package com.gomo.app.common;

import org.flywaydb.core.Flyway;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import com.zaxxer.hikari.HikariDataSource;

public class DatabaseContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	public static MySQLContainer<?> mysqlContainer;
	public static final GenericContainer<?> redisContainer;

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
	}

	@Override
	public void initialize(ConfigurableApplicationContext context) {
		TestPropertyValues.of(
			"spring.datasource.url=" + mysqlContainer.getJdbcUrl(),
			"spring.datasource.username=" + mysqlContainer.getUsername(),
			"spring.datasource.password=" + mysqlContainer.getPassword(),
			"spring.data.redis.host=" + redisContainer.getHost(),
			"spring.data.redis.port=" + redisContainer.getMappedPort(6379)
		).applyTo(context.getEnvironment());
	}
}
