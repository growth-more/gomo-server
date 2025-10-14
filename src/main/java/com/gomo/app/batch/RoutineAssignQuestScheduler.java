package com.gomo.app.batch;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gomo.app.core.quest.domain.model.quest.QuestType;

@Configuration
public class RoutineAssignQuestScheduler {

	@Bean
	public JobDetail dailyQuestJobDetail() {
		return JobBuilder.newJob(RoutineAssignQuestLauncher.class)
			.withIdentity("dailyQuestJob")
			.usingJobData(jobDataMap(QuestType.DAILY.name()))
			.storeDurably()
			.build();
	}

	@Bean
	public Trigger dailyQuestJobTrigger() {
		return TriggerBuilder.newTrigger()
			.forJob(dailyQuestJobDetail())
			.withIdentity("dailyQuestTrigger")
			.withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(0, 0))
			.build();
	}

	@Bean
	public JobDetail weeklyQuestJobDetail() {
		return JobBuilder.newJob(RoutineAssignQuestLauncher.class)
			.withIdentity("weeklyQuestJob")
			.usingJobData(jobDataMap(QuestType.WEEKLY.name()))
			.storeDurably()
			.build();
	}

	@Bean
	public Trigger weeklyQuestJobTrigger() {
		return TriggerBuilder.newTrigger()
			.forJob(weeklyQuestJobDetail())
			.withIdentity("weeklyQuestTrigger")
			.withSchedule(CronScheduleBuilder.weeklyOnDayAndHourAndMinute(1, 0, 0))
			.build();
	}

	@Bean
	public JobDetail monthlyQuestJobDetail() {
		return JobBuilder.newJob(RoutineAssignQuestLauncher.class)
			.withIdentity("monthlyQuestJob")
			.usingJobData(jobDataMap(QuestType.MONTHLY.name()))
			.storeDurably()
			.build();
	}

	@Bean
	public Trigger monthlyQuestJobTrigger() {
		return TriggerBuilder.newTrigger()
			.forJob(monthlyQuestJobDetail())
			.withIdentity("monthlyQuestTrigger")
			.withSchedule(CronScheduleBuilder.monthlyOnDayAndHourAndMinute(1, 0, 0))
			.build();
	}

	private JobDataMap jobDataMap(String questType) {
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("jobName", "routineAssignQuestBatchJob");
		jobDataMap.put("questType", questType);
		return jobDataMap;
	}
}
