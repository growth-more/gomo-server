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
public class FillQuestPoolLauncher extends QuartzJobBean {

	private final JobLauncher jobLauncher;
	private final Job fillQuestPoolJob;

	public FillQuestPoolLauncher(JobLauncher jobLauncher, @Qualifier("fillQuestPoolJob") Job fillQuestPoolJob) {
		this.jobLauncher = jobLauncher;
		this.fillQuestPoolJob = fillQuestPoolJob;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) {
		JobParameters jobParameters = new JobParametersBuilder()
			.addString("questType", context.getJobDetail().getJobDataMap().getString("questType"))
			.addLong("limitPerMember", context.getJobDetail().getJobDataMap().getLong("limitPerMember"))
			.addString("timestamp", LocalDate.now().toString())
			.toJobParameters();
		try {
			log.info("Starting job with parameters: {}", jobParameters);
			jobLauncher.run(fillQuestPoolJob, jobParameters);
		} catch (Exception e) {
			log.error("Job execution failed", e);
		}
	}
}
