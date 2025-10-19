package com.gomo.app.batch;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import com.gomo.app.core.member.domain.model.ActivateStatus;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.repository.MemberRepository;
import com.gomo.app.core.quest.application.port.RoutineAssignQuestPortIn;
import com.gomo.app.core.quest.application.port.dto.ParticipantDto;

/**
 * Creates assign quests for all active members for a specific routine cycle (e.g., daily, weekly).
 * <p>
 * It creates both repeat quests and quest pool.
 * This is intended to be called by a scheduler.
 */
@Configuration
public class RoutineAssignQuestBatch {

	private static final String JOB_NAME = "assignQuestJob";
	private static final int CHUNK_SIZE = 100;

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final MemberRepository memberRepository;
	private final RoutineAssignQuestPortIn routineAssignQuestPortIn;

	public RoutineAssignQuestBatch(JobRepository jobRepository, @Qualifier("metaTransactionManager") PlatformTransactionManager metaTransactionManager,
		MemberRepository memberRepository, RoutineAssignQuestPortIn routineAssignQuestPortIn) {
		this.jobRepository = jobRepository;
		this.transactionManager = metaTransactionManager;
		this.memberRepository = memberRepository;
		this.routineAssignQuestPortIn = routineAssignQuestPortIn;
	}

	@Bean
	public Job routineAssignQuestJob() {
		return new JobBuilder(JOB_NAME, jobRepository)
			.start(routineAssignQuestStep())
			.build();
	}

	@Bean
	@JobScope
	public Step routineAssignQuestStep() {
		return new StepBuilder("assignQuestStep", jobRepository)
			.<Member, ParticipantDto>chunk(CHUNK_SIZE, transactionManager)
			.reader(routineAssignQuestReader(null))
			.processor(assignQuestProcessor())
			.writer(assignQuestWriter(null))
			.faultTolerant()
			.retryLimit(3)
			.retry(Exception.class) // TODO [2025-10-14] jhl221123 : 우선 모든 예외를 대상으로 하고, 추후 운영을 통해 범위를 좁혀야 합니다.
			.skip(Exception.class)
			.skipLimit(Integer.MAX_VALUE)
			.listener(new RoutineAssignQuestSkipListener())
			.build();
	}

	@Bean
	@StepScope
	public RepositoryItemReader<Member> routineAssignQuestReader(@Value("#{jobParameters['questType']}") String questType) {
		return new RepositoryItemReaderBuilder<Member>()
			.name("memberReader")
			.pageSize(CHUNK_SIZE)
			.methodName("findByActivateStatusAndLastLoginDateTimeGreaterThanEqual")
			.repository(memberRepository)
			.arguments(List.of(ActivateStatus.ACTIVE, getLoginCutoffDateTime(questType)))
			.sorts(Map.of("id", Sort.Direction.ASC))
			.build();
	}

	@Bean
	public ItemProcessor<Member, ParticipantDto> assignQuestProcessor() {
		return member -> ParticipantDto.of(
			member.getId(),
			member.getQuestProperty().dailyThreshold(),
			member.getQuestProperty().weeklyThreshold(),
			member.getQuestProperty().monthlyThreshold()
		);
	}

	@Bean
	@StepScope
	public ItemWriter<ParticipantDto> assignQuestWriter(@Value("#{jobParameters['questType']}") String questType) {
		return chunk -> {
			List<ParticipantDto> participantDtos = new ArrayList<>(chunk.getItems());
			routineAssignQuestPortIn.execute(participantDtos, questType);
		};
	}

	private LocalDateTime getLoginCutoffDateTime(String questType) {
		return switch (questType) {
			case "DAILY" -> LocalDate.now().minusDays(1).atStartOfDay();
			case "WEEKLY" -> LocalDate.now().minusWeeks(1).atStartOfDay();
			case "MONTHLY" -> LocalDate.now().minusMonths(1).atStartOfDay();
			default -> throw new IllegalStateException("Unknown quest type: " + questType);
		};
	}
}
