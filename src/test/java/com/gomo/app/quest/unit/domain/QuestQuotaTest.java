package com.gomo.app.quest.unit.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.quest.domain.model.QuestQuota;
import com.gomo.app.quest.domain.model.QuestType;

@DisplayName("[Domain unit]: 퀘스트 할당량 값 객체 테스트")
public class QuestQuotaTest {

	@DisplayName("일일 퀘스트 할당량을 초과하지 않는다.")
	@Test
	void not_exceed_daily_quest_quota() {
		QuestQuota questQuota = QuestQuota.of(5, 5, 5);

		boolean actual = questQuota.isExceeded(QuestType.DAILY, 4);

		assertThat(actual).isFalse();
	}

	@DisplayName("일일 퀘스트 할당량을 초과한다.")
	@Test
	void exceed_daily_quest_quota() {
		QuestQuota questQuota = QuestQuota.of(5, 5, 5);

		boolean actual = questQuota.isExceeded(QuestType.DAILY, 5);

		assertThat(actual).isTrue();
	}

	@DisplayName("주간 퀘스트 할당량을 초과하지 않는다.")
	@Test
	void not_exceed_weekly_quest_quota() {
		QuestQuota questQuota = QuestQuota.of(5, 5, 5);

		boolean actual = questQuota.isExceeded(QuestType.WEEKLY, 4);

		assertThat(actual).isFalse();
	}

	@DisplayName("주간 퀘스트 할당량을 초과한다.")
	@Test
	void exceed_weekly_quest_quota() {
		QuestQuota questQuota = QuestQuota.of(5, 5, 5);

		boolean actual = questQuota.isExceeded(QuestType.WEEKLY, 5);

		assertThat(actual).isTrue();
	}

	@DisplayName("월간 퀘스트 할당량을 초과하지 않는다.")
	@Test
	void not_exceed_monthly_quest_quota() {
		QuestQuota questQuota = QuestQuota.of(5, 5, 5);

		boolean actual = questQuota.isExceeded(QuestType.MONTHLY, 4);

		assertThat(actual).isFalse();
	}

	@DisplayName("월간 퀘스트 할당량을 초과한다.")
	@Test
	void exceed_monthly_quest_quota() {
		QuestQuota questQuota = QuestQuota.of(5, 5, 5);

		boolean actual = questQuota.isExceeded(QuestType.MONTHLY, 5);

		assertThat(actual).isTrue();
	}
}
