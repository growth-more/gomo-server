package com.gomo.app.quest.integration;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.quest.common.util.AssignQuestDataHelper;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.domain.service.ScheduleAssignQuestService;

@DisplayName("[Domain integration]: 참여 퀘스트 생성 스케줄링 기능 테스트")
public class ScheduleAssignQuestServiceTest extends IntegrationTestBase {

	@Autowired
	ScheduleAssignQuestService sut;

	@Autowired
	AssignQuestRepository assignQuestRepository;

	@Autowired
	AssignQuestDataHelper assignQuestDataHelper;

	@AfterEach
	public void tearDown() {
		assignQuestDataHelper.cleanUp();
	}

	@DisplayName("계정 상태가 활성화 되어있고, 전날까지 로그인한 회원들의 참여 퀘스트를 생성한다.")
	@Test
	void create_participating_quests() {
		sut.createParticipatingQuestOfAllActiveMember();

		List<AssignQuest> all = assignQuestRepository.findAll();
		Assertions.assertThat(all.size()).isEqualTo(28);
	}
}
