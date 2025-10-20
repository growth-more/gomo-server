package com.gomo.app.test.container;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import com.zaxxer.hikari.HikariDataSource;

public class MetaDBContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	public static MySQLContainer<?> metaMysqlContainer;

	static {
		metaMysqlContainer = new MySQLContainer<>("mysql:8.0.28")
			.withDatabaseName("gomo_meta")
			.withUsername("gomo")
			.withPassword("1111")
			.withReuse(true)
			.withUrlParam("serverTimezone", "Asia/Seoul")
			.waitingFor(Wait.forListeningPort());
		metaMysqlContainer.start();

		HikariDataSource ds = new HikariDataSource();
		ds.setJdbcUrl(metaMysqlContainer.getJdbcUrl());
		ds.setUsername(metaMysqlContainer.getUsername());
		ds.setPassword(metaMysqlContainer.getPassword());
	}

	@Override
	public void initialize(ConfigurableApplicationContext context) {
		TestPropertyValues.of(
			"spring.datasource.meta.url=" + metaMysqlContainer.getJdbcUrl(),
			"spring.datasource.meta.username=" + metaMysqlContainer.getUsername(),
			"spring.datasource.meta.password=" + metaMysqlContainer.getPassword()
		).applyTo(context.getEnvironment());
	}
}
