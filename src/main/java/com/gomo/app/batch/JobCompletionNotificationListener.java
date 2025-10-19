package com.gomo.app.batch;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JobCompletionNotificationListener implements JobExecutionListener {

	@Override
	public void afterJob(JobExecution jobExecution) {
		long totalItems = jobExecution.getStepExecutions().stream()
			.mapToLong(StepExecution::getReadCount)
			.sum();
		long successItems = jobExecution.getStepExecutions().stream()
			.mapToLong(StepExecution::getWriteCount)
			.sum();
		long retryCount = jobExecution.getStepExecutions().stream()
			.mapToLong(StepExecution::getReadSkipCount)
			.sum();
		long skipCount = jobExecution.getStepExecutions().stream()
			.mapToLong(StepExecution::getSkipCount)
			.sum();

		String jobName = jobExecution.getJobInstance().getJobName();
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("[BATCH] {} Success. total item: {}, success item: {}, total retry count: {}, skip count: {}",
				jobName,
				totalItems,
				successItems,
				retryCount,
				skipCount);
		} else if (jobExecution.getStatus() == BatchStatus.FAILED) {
			log.error("[BATCH] {} Failed with exceptions.", jobName);
			jobExecution.getAllFailureExceptions().forEach(e -> log.error("Exception: ", e));
		}
	}
}
