package com.gomo.app.quest.common.util;

import javax.sql.DataSource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RepeatQuestDataHelper {

	private final DataSource dataSource;

	public void cleanUp() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
			new ClassPathResource("database/sql/repeat-quest-cleanup.sql")
		);
		populator.execute(dataSource);
	}
}
