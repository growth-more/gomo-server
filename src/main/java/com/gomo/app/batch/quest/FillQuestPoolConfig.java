package com.gomo.app.batch.quest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import com.gomo.app.batch.JobCompletionNotificationListener;
import com.gomo.app.core.member.domain.model.ActivateStatus;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.repository.MemberRepository;
import com.gomo.app.core.quest.application.port.PublishCreateQuestPoolPortIn;
import com.gomo.app.core.quest.application.port.command.PublishCreateQuestPoolCommand;
import com.gomo.app.core.quest.domain.model.participant.Participant;
import com.gomo.app.core.quest.domain.model.participant.QuestQuota;

@Configuration
public class FillQuestPoolConfig {

	private static final String JOB_NAME = "fillQuestPoolJob";
	private static final int CHUNK_SIZE = 100;

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final MemberRepository memberRepository;
	private final PublishCreateQuestPoolPortIn publishCreateQuestPoolPortIn;

	public FillQuestPoolConfig(JobRepository jobRepository, @Qualifier("metaTransactionManager") PlatformTransactionManager metaTransactionManager,
		MemberRepository memberRepository, PublishCreateQuestPoolPortIn publishCreateQuestPoolPortIn) {
		this.jobRepository = jobRepository;
		this.transactionManager = metaTransactionManager;
		this.memberRepository = memberRepository;
		this.publishCreateQuestPoolPortIn = publishCreateQuestPoolPortIn;
	}

	@Bean
	public Job fillQuestPoolJob() {
		return new JobBuilder(JOB_NAME, jobRepository)
			.listener(new JobCompletionNotificationListener())
			.start(fillQuestPoolStep())
			.build();
	}

	@Bean
	@JobScope
	public Step fillQuestPoolStep() {
		return new StepBuilder("fillQuestPoolStep", jobRepository)
			.<Member, Member>chunk(CHUNK_SIZE, transactionManager)
			.reader(fillQuestPoolReader(null))
			.writer(fillQuestPoolEventWriter(null, null))
			.faultTolerant()
			.retryLimit(3)
			.retry(Exception.class) // TODO [2025-10-14] jhl221123 : 우선 모든 예외를 대상으로 하고, 추후 운영을 통해 범위를 좁혀야 합니다.
			.skip(Exception.class)
			.skipLimit(Integer.MAX_VALUE)
			.listener(new FillQuestPoolSkipListener())
			.build();
	}

	@Bean
	@StepScope
	public RepositoryItemReader<Member> fillQuestPoolReader(@Value("#{jobParameters['questType']}") String questType) {
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
	@StepScope
	public ItemWriter<Member> fillQuestPoolEventWriter(
		@Value("#{jobParameters['limitPerMember']}") Long limitPerMember,
		@Value("#{jobParameters['questType']}") String questType
	) {
		return chunk -> {
			PublishCreateQuestPoolCommand command = PublishCreateQuestPoolCommand.of(createParticipants(chunk), questType, limitPerMember);
			publishCreateQuestPoolPortIn.publish(command);
		};
	}

	@NotNull
	private List<Participant> createParticipants(Chunk<? extends Member> chunk) {
		return chunk.getItems().stream()
			.map(member ->
				Participant.of(
					member.getId(),
					QuestQuota.of(
						member.getQuestProperty().dailyThreshold(),
						member.getQuestProperty().weeklyThreshold(),
						member.getQuestProperty().monthlyThreshold()
					)
				)
			).toList();
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
