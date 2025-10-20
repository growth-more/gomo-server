package com.gomo.app.batch.quest;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gomo.app.batch.JobCompletionNotificationListener;

@Configuration
public class RoutineAssignQuestConfig {

	private final JobRepository jobRepository;

	public RoutineAssignQuestConfig(JobRepository jobRepository) {
		this.jobRepository = jobRepository;
	}

	@Bean
	public Job routineAssignQuestJob(@Qualifier("fillQuestPoolStep") Step fillQuestPoolStep, @Qualifier("createAssignQuestStep") Step createAssignQuestStep) {
		return new JobBuilder("routineAssignQuestJob", jobRepository)
			.listener(new JobCompletionNotificationListener())
			.start(fillQuestPoolStep)
			.next(createAssignQuestStep)
			.build();
	}
}
