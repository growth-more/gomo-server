package com.gomo.app.config.db;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class MetaDBConfig {

	private final MetaDBProperties properties;

	@Bean
	@BatchDataSource
	@QuartzDataSource
	public DataSource metaDataSource() {
		return DataSourceBuilder.create()
			.url(properties.getUrl())
			.username(properties.getUsername())
			.password(properties.getPassword())
			.driverClassName(properties.getDriverClassName())
			.build();
	}

	@Bean
	public PlatformTransactionManager metaTransactionManager() {
		return new DataSourceTransactionManager(metaDataSource());
	}
}
