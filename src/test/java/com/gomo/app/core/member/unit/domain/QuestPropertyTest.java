package com.gomo.app.core.member.unit.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.domain.model.QuestProperty;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Domain unit]: 퀘스트 설정 테스트")
public class QuestPropertyTest {

	@DisplayName("기본 퀘스트 설정을 생성한다.")
	@Test
	void create_quest_property_with_default_values() {
		QuestProperty questProperty = QuestProperty.createDefault();

		assertThat(questProperty.getDailyThreshold()).isNotNull();
		assertThat(questProperty.getWeeklyThreshold()).isNotNull();
		assertThat(questProperty.getMonthlyThreshold()).isNotNull();
	}
}
