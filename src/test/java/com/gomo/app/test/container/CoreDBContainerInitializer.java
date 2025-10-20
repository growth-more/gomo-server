package com.gomo.app.test.container;

import org.flywaydb.core.Flyway;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import com.zaxxer.hikari.HikariDataSource;

public class CoreDBContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	public static MySQLContainer<?> coreMysqlContainer;

	static {
		coreMysqlContainer = new MySQLContainer<>("mysql:8.0.28")
			.withDatabaseName("gomo")
			.withUsername("gomo")
			.withPassword("1111")
			.withReuse(true)
			.withUrlParam("serverTimezone", "Asia/Seoul")
			.waitingFor(Wait.forListeningPort());
		coreMysqlContainer.start();

		HikariDataSource ds = new HikariDataSource();
		ds.setJdbcUrl(coreMysqlContainer.getJdbcUrl());
		ds.setUsername(coreMysqlContainer.getUsername());
		ds.setPassword(coreMysqlContainer.getPassword());

		Flyway flyway = Flyway.configure()
			.dataSource(ds)
			.locations("classpath:db/migration")
			.load();
		flyway.migrate();
	}

	@Override
	public void initialize(ConfigurableApplicationContext context) {
		TestPropertyValues.of(
			"spring.datasource.core.url=" + coreMysqlContainer.getJdbcUrl(),
			"spring.datasource.core.username=" + coreMysqlContainer.getUsername(),
			"spring.datasource.core.password=" + coreMysqlContainer.getPassword()
		).applyTo(context.getEnvironment());
	}
}
