package com.gomo.app.interest.common.util;

import javax.sql.DataSource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InterestRelationDBDataHelper {

	private final DataSource dataSource;

	public void cleanUp() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
			new ClassPathResource("database/sql/interest-relation-cleanup.sql")
		);
		populator.execute(dataSource);
	}
}
