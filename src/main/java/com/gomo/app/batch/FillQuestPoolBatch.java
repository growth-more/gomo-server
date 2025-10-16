package com.gomo.app.batch;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import com.gomo.app.common.event.EventRouter;
import com.gomo.app.common.util.JsonParser;
import com.gomo.app.common.util.TimestampGenerator;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.core.member.domain.model.ActivateStatus;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.repository.MemberRepository;
import com.gomo.app.core.quest.event.FillQuestPoolEvent;
import com.gomo.app.support.messagebroker.application.port.PublishMessagePortIn;

@Configuration
public class FillQuestPoolBatch {

	private static final String JOB_NAME = "fillQuestPoolJob";
	private static final int CHUNK_SIZE = 100;

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final MemberRepository memberRepository;
	private final InterestRepository interestRepository;
	private final EventRouter eventRouter;
	private final PublishMessagePortIn publishMessagePortIn;

	public FillQuestPoolBatch(JobRepository jobRepository, @Qualifier("metaTransactionManager") PlatformTransactionManager metaTransactionManager,
		MemberRepository memberRepository, InterestRepository interestRepository, EventRouter eventRouter, PublishMessagePortIn publishMessagePortIn) {
		this.jobRepository = jobRepository;
		this.transactionManager = metaTransactionManager;
		this.memberRepository = memberRepository;
		this.interestRepository = interestRepository;
		this.eventRouter = eventRouter;
		this.publishMessagePortIn = publishMessagePortIn;
	}

	@Bean
	public Job fillQuestPoolJob() {
		return new JobBuilder(JOB_NAME, jobRepository)
			.start(fillQuestPoolStep())
			.build();
	}

	@Bean
	@JobScope
	public Step fillQuestPoolStep() {
		return new StepBuilder("fillQuestPoolStep", jobRepository)
			.<Member, Member>chunk(CHUNK_SIZE, transactionManager)
			.reader(fillQuestPoolReader(null))
			.writer(fillQuestPoolEventWriter(null))
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
	public ItemWriter<Member> fillQuestPoolEventWriter(@Value("#{jobParameters['limitPerMember']}") Long limitPerMember) {
		// TODO [2025-10-17] jhl221123 : 퀘스트 모듈의 서비스로 분리해야 합니다. 그리고 회원 가입 시에도 초기 quest pool을 채우기 위해 분리된 해당 서비스를 사용해야합니다.
		return chunk -> {
			Set<UUID> memberIds = chunk.getItems().stream()
				.map(Member::getId)
				.collect(Collectors.toSet());

			Map<UUID, List<Interest>> interestsByMemberId = interestRepository.findAllByRegistrantIdIn(memberIds).stream()
				.collect(Collectors.groupingBy(Interest::getRegistrantId));

			for (Member member : chunk.getItems()) {
				UUID participantId = member.getId();
				List<FillQuestPoolEvent.Subject> subjects = interestsByMemberId.getOrDefault(participantId, List.of()).stream()
					.map(interest -> FillQuestPoolEvent.Subject.of(
						interest.getId(),
						interest.name(),
						interest.getProficiency().level()))
					.toList();

				FillQuestPoolEvent fillQuestPoolEvent = FillQuestPoolEvent.of(participantId, subjects, limitPerMember, TimestampGenerator.generate());
				String eventName = fillQuestPoolEvent.getClass().getSimpleName();
				String exchange = eventRouter.getExchange(eventName);
				String routingKey = eventRouter.getRoutingKey(eventName);
				publishMessagePortIn.send(exchange, routingKey, JsonParser.toJson(fillQuestPoolEvent));
			}
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
