package com.gomo.app.config.db;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableJpaRepositories(
	basePackages = {"com.gomo.app.core", "com.gomo.app.support"},
	entityManagerFactoryRef = "coreEntityManager",
	transactionManagerRef = "coreTransactionManager"
)
public class CoreDBConfig {

	private final CoreDBProperties properties;

	@Primary
	@Bean
	public DataSource coreDBSource() {
		return DataSourceBuilder.create()
			.url(properties.getUrl())
			.username(properties.getUsername())
			.password(properties.getPassword())
			.driverClassName(properties.getDriverClassName())
			.build();
	}

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean coreEntityManager(EntityManagerFactoryBuilder builder) {
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", "none");
		properties.put("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
		properties.put("hibernate.implicit_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");

		return builder
			.dataSource(coreDBSource())
			.packages("com.gomo.app.core", "com.gomo.app.support")
			.persistenceUnit("core")
			.properties(properties)
			.build();
	}

	@Primary
	@Bean
	public PlatformTransactionManager coreTransactionManager(EntityManagerFactoryBuilder builder) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(coreEntityManager(builder).getObject());
		return transactionManager;
	}
}
