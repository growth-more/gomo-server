package com.gomo.app.batch;

import java.time.LocalDate;

import org.quartz.JobExecutionContext;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RoutineAssignQuestLauncher extends QuartzJobBean {

	private final JobLauncher jobLauncher;
	private final Job routineAssignQuestJob;

	public RoutineAssignQuestLauncher(JobLauncher jobLauncher, @Qualifier("routineAssignQuestJob") Job routineAssignQuestJob) {
		this.jobLauncher = jobLauncher;
		this.routineAssignQuestJob = routineAssignQuestJob;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) {
		JobParameters jobParameters = new JobParametersBuilder()
			.addString("questType", context.getJobDetail().getJobDataMap().getString("questType"))
			.addString("timestamp", LocalDate.now().toString())
			.toJobParameters();
		try {
			log.info("Starting job with parameters: {}", jobParameters);
			jobLauncher.run(routineAssignQuestJob, jobParameters);
		} catch (Exception e) {
			log.error("Job execution failed", e);
		}
	}
}
