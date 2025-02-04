package com.gomo.app.quest.unit.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.quest.domain.model.QuestScorePolicy;
import com.gomo.app.quest.domain.model.QuestType;

@DisplayName("[Domain unit]: 퀘스트 점수 정책 생성 테스트")
public class QuestScorePolicyTest {

	@DisplayName("퀘스트 점수 정책을 생성한다.")
	@Test
	void create_quest_score_policy() {
		QuestScorePolicy questScorePolicy = QuestScorePolicy.of(QuestType.DAILY, 2);

		assertThat(questScorePolicy.getQuestType()).isEqualTo(QuestType.DAILY);
		assertThat(questScorePolicy.getScore()).isEqualTo(2);
	}
}
