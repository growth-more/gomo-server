package com.gomo.app.quest.unit.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.quest.domain.service.AssignQuestService;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Domain unit]: 할당 퀘스트 생성 테스트")
public class AssignQuestServiceTest {

	@InjectMocks
	AssignQuestService assignQuestService;

	@Mock
	AssignQuestRepository assignQuestRepository;

	@Mock
	RepeatQuestRepository repeatQuestRepository;

	@DisplayName("모든 회원의 할당 퀘스트를 생성한다.")
	@Test
	void create_assign_quest() {

	}
}
