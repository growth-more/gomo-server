package com.gomo.app.test.container;

import org.flywaydb.core.Flyway;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import com.zaxxer.hikari.HikariDataSource;

public class MySQLContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	public static MySQLContainer<?> mysqlContainer;

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
	}

	@Override
	public void initialize(ConfigurableApplicationContext context) {
		TestPropertyValues.of(
			"spring.datasource.url=" + mysqlContainer.getJdbcUrl(),
			"spring.datasource.username=" + mysqlContainer.getUsername(),
			"spring.datasource.password=" + mysqlContainer.getPassword()
		).applyTo(context.getEnvironment());
	}
}
