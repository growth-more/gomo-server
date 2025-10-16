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
public class FillQuestPoolScheduler {

	private static final String JOB_GROUP_NAME = "FILL_QUEST_POOL_JOBS";
	private static final long LIMIT_PER_MEMBER = 20;

	@Bean
	public JobDetail dailyFillQuestPoolJobDetail() {
		return JobBuilder.newJob(FillQuestPoolLauncher.class)
			.withIdentity("dailyFillQuestPoolJobDetail", JOB_GROUP_NAME)
			.usingJobData(jobDataMap(QuestType.DAILY.name(), LIMIT_PER_MEMBER))
			.storeDurably()
			.build();
	}

	@Bean
	public Trigger dailyFillQuestPoolJobTrigger() {
		return TriggerBuilder.newTrigger()
			.forJob(dailyFillQuestPoolJobDetail())
			.withIdentity("dailyFillQuestPoolJobTrigger", JOB_GROUP_NAME)
			.withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(16, 0))
			.build();
	}

	@Bean
	public JobDetail weeklyFillQuestPoolJobDetail() {
		return JobBuilder.newJob(FillQuestPoolLauncher.class)
			.withIdentity("weeklyFillQuestPoolJobDetail", JOB_GROUP_NAME)
			.usingJobData(jobDataMap(QuestType.WEEKLY.name(), LIMIT_PER_MEMBER))
			.storeDurably()
			.build();
	}

	@Bean
	public Trigger weeklyFillQuestPoolJobTrigger() {
		return TriggerBuilder.newTrigger()
			.forJob(weeklyFillQuestPoolJobDetail())
			.withIdentity("weeklyFillQuestPoolJobTrigger", JOB_GROUP_NAME)
			.withSchedule(CronScheduleBuilder.weeklyOnDayAndHourAndMinute(7, 16, 0))
			.build();
	}

	@Bean
	public JobDetail monthlyFillQuestPoolJobDetail() {
		return JobBuilder.newJob(FillQuestPoolLauncher.class)
			.withIdentity("monthlyFillQuestPoolJobDetail", JOB_GROUP_NAME)
			.usingJobData(jobDataMap(QuestType.MONTHLY.name(), LIMIT_PER_MEMBER))
			.storeDurably()
			.build();
	}

	@Bean
	public Trigger monthlyFillQuestPoolJobTrigger() {
		return TriggerBuilder.newTrigger()
			.forJob(monthlyFillQuestPoolJobDetail())
			.withIdentity("monthlyFillQuestPoolJobTrigger", JOB_GROUP_NAME)
			.withSchedule(CronScheduleBuilder.monthlyOnDayAndHourAndMinute(30, 16, 0))
			.build();
	}

	private JobDataMap jobDataMap(String questType, Long limitPerMember) {
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("jobName", "fillQuestPoolJob");
		jobDataMap.put("questType", questType);
		jobDataMap.put("limitPerMember", limitPerMember);
		return jobDataMap;
	}
}
