package com.gomo.app.batch.quest;

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

	private static final String JOB_GROUP_NAME = "ROUTINE_ASSIGN_QUEST_JOBS";
	private static final long LIMIT_PER_MEMBER = 20;

	@Bean
	public JobDetail dailyRoutineAssignQuestJobDetail() {
		return JobBuilder.newJob(RoutineAssignQuestLauncher.class)
			.withIdentity("dailyQuestWorkflowJobDetail", JOB_GROUP_NAME)
			.usingJobData(jobDataMap(QuestType.DAILY.name(), LIMIT_PER_MEMBER))
			.storeDurably()
			.build();
	}

	@Bean
	public Trigger dailyRoutineAssignQuestJobTrigger() {
		return TriggerBuilder.newTrigger()
			.forJob(dailyRoutineAssignQuestJobDetail())
			.withIdentity("dailyQuestWorkflowJobTrigger", JOB_GROUP_NAME)
			.withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(0, 0))
			.build();
	}

	// Weekly Trigger
	@Bean
	public JobDetail weeklyRoutineAssignQuestJobDetail() {
		return JobBuilder.newJob(RoutineAssignQuestLauncher.class)
			.withIdentity("weeklyQuestWorkflowJobDetail", JOB_GROUP_NAME)
			.usingJobData(jobDataMap(QuestType.WEEKLY.name(), LIMIT_PER_MEMBER))
			.storeDurably()
			.build();
	}

	@Bean
	public Trigger weeklyRoutineAssignQuestJobTrigger() {
		return TriggerBuilder.newTrigger()
			.forJob(weeklyRoutineAssignQuestJobDetail())
			.withIdentity("weeklyQuestWorkflowJobTrigger", JOB_GROUP_NAME)
			.withSchedule(CronScheduleBuilder.weeklyOnDayAndHourAndMinute(1, 0, 0))
			.build();
	}

	// Monthly Trigger
	@Bean
	public JobDetail monthlyRoutineAssignQuestJobDetail() {
		return JobBuilder.newJob(RoutineAssignQuestLauncher.class)
			.withIdentity("monthlyQuestWorkflowJobDetail", JOB_GROUP_NAME)
			.usingJobData(jobDataMap(QuestType.MONTHLY.name(), LIMIT_PER_MEMBER))
			.storeDurably()
			.build();
	}

	@Bean
	public Trigger monthlyRoutineAssignQuestJobTrigger() {
		return TriggerBuilder.newTrigger()
			.forJob(monthlyRoutineAssignQuestJobDetail())
			.withIdentity("monthlyQuestWorkflowJobTrigger", JOB_GROUP_NAME)
			.withSchedule(CronScheduleBuilder.monthlyOnDayAndHourAndMinute(1, 0, 0))
			.build();
	}

	private JobDataMap jobDataMap(String questType, Long limitPerMember) {
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("questType", questType);
		jobDataMap.put("limitPerMember", limitPerMember);
		return jobDataMap;
	}
}
