package com.gomo.app.quest.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.quest.application.ReadRepeatQuestUseCase;
import com.gomo.app.quest.common.fixture.QuestRewardPolicyFixture;
import com.gomo.app.quest.common.fixture.RepeatQuestFixture;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.domain.repository.QuestRewardPolicyRepository;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.quest.presentation.response.ListRepeatQuestResponse;

@DisplayName("[Application unit]: 반복 퀘스트 조회 테스트")
@ExtendWith(MockitoExtension.class)
public class ReadRepeatQuestUseCaseTest {

	@InjectMocks
	private ReadRepeatQuestUseCase sut;

	@Mock
	private RepeatQuestRepository repeatQuestRepository;

	@Mock
	private QuestRewardPolicyRepository questRewardPolicyRepository;

	@DisplayName("반복 퀘스트 목록을 조회한다.")
	@Test
	void find_All() {
		List<RepeatQuest> dailyQuests = List.of(RepeatQuestFixture.repeatQuest(QuestType.DAILY), RepeatQuestFixture.repeatQuest(QuestType.DAILY));
		List<RepeatQuest> weeklyQuests = List.of(RepeatQuestFixture.repeatQuest(QuestType.WEEKLY));
		List<RepeatQuest> monthlyQuests = List.of();

		doReturn(QuestRewardPolicyFixture.point()).when(questRewardPolicyRepository).findPointPolicies();
		doReturn(QuestRewardPolicyFixture.score()).when(questRewardPolicyRepository).findScorePolicies();
		doReturn(dailyQuests).when(repeatQuestRepository).findRepeatQuestsByQuestType(any(), eq(QuestType.DAILY));
		doReturn(weeklyQuests).when(repeatQuestRepository).findRepeatQuestsByQuestType(any(), eq(QuestType.WEEKLY));
		doReturn(monthlyQuests).when(repeatQuestRepository).findRepeatQuestsByQuestType(any(), eq(QuestType.MONTHLY));

		ListRepeatQuestResponse actual = sut.findAll(ParticipantId.of(UUID.randomUUID()));

		assertThat(actual.getDailyQuests().size()).isEqualTo(2);
		assertThat(actual.getWeeklyQuests().size()).isEqualTo(1);
		assertThat(actual.getMonthlyQuests().size()).isEqualTo(0);
		assertThat(actual.getDailyQuests())
			.extracting("point")
			.containsExactly(10, 10);
		assertThat(actual.getDailyQuests())
			.extracting("score")
			.containsExactly(2, 2);
		assertThat(actual.getWeeklyQuests())
			.extracting("point")
			.containsExactly(150);
		assertThat(actual.getWeeklyQuests())
			.extracting("score")
			.containsExactly(20);
	}
}
