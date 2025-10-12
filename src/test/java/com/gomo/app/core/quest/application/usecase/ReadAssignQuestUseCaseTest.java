package com.gomo.app.core.quest.application.usecase;

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

import com.gomo.app.core.quest.application.port.dto.ListAssignQuestDto;
import com.gomo.app.core.quest.domain.model.assign.AssignQuest;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.core.quest.domain.service.QuestRewardService;
import com.gomo.app.core.quest.fixture.AssignQuestFixture;
import com.gomo.app.core.quest.fixture.QuestRewardFixture;

@DisplayName("[Application unit]: 현재 참여중인 퀘스트 조회 테스트")
@ExtendWith(MockitoExtension.class)
public class ReadAssignQuestUseCaseTest {

	@InjectMocks
	private ReadAssignQuestUseCase sut;

	@Mock
	private AssignQuestRepository assignQuestRepository;

	@Mock
	private QuestRewardService questRewardService;

	@DisplayName("현재 참여중인 목록을 조회한다.")
	@Test
	void find_All() {
		List<AssignQuest> dailyQuests = List.of(AssignQuestFixture.create(QuestType.DAILY), AssignQuestFixture.create(QuestType.DAILY));
		List<AssignQuest> weeklyQuests = List.of(AssignQuestFixture.create(QuestType.WEEKLY));
		List<AssignQuest> monthlyQuests = List.of();
		doReturn(QuestRewardFixture.create(1, 10)).when(questRewardService).find(eq(QuestType.DAILY));
		doReturn(QuestRewardFixture.create(2, 20)).when(questRewardService).find(eq(QuestType.WEEKLY));
		doReturn(dailyQuests).when(assignQuestRepository).findParticipatingQuestByQuestType(any(), eq(QuestType.DAILY), any(), any());
		doReturn(weeklyQuests).when(assignQuestRepository).findParticipatingQuestByQuestType(any(), eq(QuestType.WEEKLY), any(), any());
		doReturn(monthlyQuests).when(assignQuestRepository).findParticipatingQuestByQuestType(any(), eq(QuestType.MONTHLY), any(), any());

		ListAssignQuestDto actual = sut.findAll(UUID.randomUUID());

		assertThat(actual.dailyQuests().size()).isEqualTo(2);
		assertThat(actual.dailyQuests()).extracting("score").containsExactly(1, 1);
		assertThat(actual.dailyQuests()).extracting("point").containsExactly(10, 10);
		assertThat(actual.weeklyQuests().size()).isEqualTo(1);
		assertThat(actual.weeklyQuests()).extracting("score").containsExactly(2);
		assertThat(actual.weeklyQuests()).extracting("point").containsExactly(20);
		assertThat(actual.monthlyQuests().size()).isEqualTo(0);
	}
}
