package com.gomo.app.core.quest.unit.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.core.quest.domain.model.QuestPointPolicy;
import com.gomo.app.core.quest.domain.model.QuestType;

@DisplayName("[Domain unit]: 퀘스트 포인트 정책 생성 테스트")
public class QuestPointPolicyTest {

	@DisplayName("퀘스트 포인트 정책을 생성한다.")
	@Test
	void create_quest_point_policy() {
		QuestPointPolicy questPointPolicy = QuestPointPolicy.of(QuestType.DAILY, 10);

		assertThat(questPointPolicy.getQuestType()).isEqualTo(QuestType.DAILY);
		assertThat(questPointPolicy.getPoints()).isEqualTo(10);
	}
}
