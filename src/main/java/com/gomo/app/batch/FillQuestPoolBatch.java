package com.gomo.app.batch;

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

import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.repository.MemberRepository;
import com.gomo.app.core.quest.domain.model.assign.AssignQuest;
import com.gomo.app.core.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.core.quest.domain.repository.QuestPoolRepository;
import com.gomo.app.core.quest.domain.repository.RepeatQuestRepository;

@Configuration
public class FillQuestPoolBatch {

	private static final String JOB_NAME = "fillQuestPoolJob";
	private static final int CHUNK_SIZE = 100;

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final MemberRepository memberRepository;
	private final AssignQuestRepository assignQuestRepository;
	private final RepeatQuestRepository repeatQuestRepository;
	private final QuestPoolRepository questPoolRepository;

	public FillQuestPoolBatch(JobRepository jobRepository, @Qualifier("metaTransactionManager") PlatformTransactionManager metaTransactionManager,
		MemberRepository memberRepository, AssignQuestRepository assignQuestRepository, RepeatQuestRepository repeatQuestRepository,
		QuestPoolRepository questPoolRepository) {
		this.jobRepository = jobRepository;
		this.transactionManager = metaTransactionManager;
		this.memberRepository = memberRepository;
		this.assignQuestRepository = assignQuestRepository;
		this.repeatQuestRepository = repeatQuestRepository;
		this.questPoolRepository = questPoolRepository;
	}

	@Bean
	public Job fillQuestPoolJob() {
		return new JobBuilder(JOB_NAME, jobRepository)
			.start(assignQuestStep())
			.build();
	}

	@Bean
	@JobScope
	public Step assignQuestStep() {
		return new StepBuilder("assignQuestStep", jobRepository)
			.<Member, List<AssignQuest>>chunk(CHUNK_SIZE, transactionManager)
			.reader(memberReader())
			.processor(assignQuestProcessor(null))
			.writer(assignQuestWriter())
			.build();
	}

	@Bean
	public RepositoryItemReader<Member> memberReader() {
		return new RepositoryItemReaderBuilder<Member>()
			.name("memberReader")
			.pageSize(10)
			.methodName("findByActivateStatusAndLastLoginDateTimeGreaterThanEqual")
			.repository(memberRepository)
			.sorts(Map.of("id", Sort.Direction.ASC))
			.build();
	}

	@Bean
	@StepScope
	public ItemProcessor<Member, List<AssignQuest>> assignQuestProcessor(@Value("#{jobParameters['questType']}") String questType) {
		return new AssignQuestProcessor(repeatQuestRepository, questPoolRepository, questType);
	}

	@Bean
	public ItemWriter<List<AssignQuest>> assignQuestWriter() {
		return chunk -> {
			List<AssignQuest> allQuestsForChunk = chunk.getItems().stream()
				.flatMap(List::stream)
				.toList();
			assignQuestRepository.saveAll(allQuestsForChunk);
		};
	}
}
