package com.gomo.app.member.unit.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.member.domain.model.DailyThreshold;
import com.gomo.app.member.domain.model.MonthlyThreshold;
import com.gomo.app.member.domain.model.QuestProperty;
import com.gomo.app.member.domain.model.WeeklyThreshold;
import com.gomo.app.member.exception.QuestPropertyConstraintViolationException;
import com.gomo.app.member.exception.code.QuestPropertyErrorCode;
import com.gomo.app.quest.domain.model.QuestType;

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

	@DisplayName("일일 퀘스트 개수가 퀘스트 제한에 도달하지 않는다.")
	@Test
	void not_reached_daily_quest_threshold() {
		QuestProperty questProperty = new QuestProperty(DailyThreshold.of(5), WeeklyThreshold.of(5), MonthlyThreshold.of(5));
		boolean actual = questProperty.hasReachedQuestThreshold(QuestType.DAILY.name(), 4);

		assertThat(actual).isFalse();
	}

	@DisplayName("일일 퀘스트 개수가 퀘스트 제한에 도달한다.")
	@Test
	void reached_daily_quest_threshold() {
		QuestProperty questProperty = new QuestProperty(DailyThreshold.of(5), WeeklyThreshold.of(5), MonthlyThreshold.of(5));
		boolean actual = questProperty.hasReachedQuestThreshold(QuestType.DAILY.name(), 5);

		assertThat(actual).isTrue();
	}

	@DisplayName("일일 퀘스트 개수가 퀘스트 제한을 초과한다.")
	@Test
	void exceed_daily_quest_threshold() {
		QuestProperty questProperty = new QuestProperty(DailyThreshold.of(5), WeeklyThreshold.of(5), MonthlyThreshold.of(5));
		boolean actual = questProperty.hasReachedQuestThreshold(QuestType.DAILY.name(), 6);

		assertThat(actual).isTrue();
	}

	@DisplayName("주간 퀘스트 개수가 퀘스트 제한에 도달하지 않는다.")
	@Test
	void not_reached_weekly_quest_threshold() {
		QuestProperty questProperty = new QuestProperty(DailyThreshold.of(5), WeeklyThreshold.of(5), MonthlyThreshold.of(5));
		boolean actual = questProperty.hasReachedQuestThreshold(QuestType.WEEKLY.name(), 4);

		assertThat(actual).isFalse();
	}

	@DisplayName("주간 퀘스트 개수가 퀘스트 제한에 도달한다.")
	@Test
	void reached_weekly_quest_threshold() {
		QuestProperty questProperty = new QuestProperty(DailyThreshold.of(5), WeeklyThreshold.of(5), MonthlyThreshold.of(5));
		boolean actual = questProperty.hasReachedQuestThreshold(QuestType.WEEKLY.name(), 5);

		assertThat(actual).isTrue();
	}

	@DisplayName("주간 퀘스트 개수가 퀘스트 제한을 초과한다.")
	@Test
	void exceed_weekly_quest_threshold() {
		QuestProperty questProperty = new QuestProperty(DailyThreshold.of(5), WeeklyThreshold.of(5), MonthlyThreshold.of(5));
		boolean actual = questProperty.hasReachedQuestThreshold(QuestType.WEEKLY.name(), 6);

		assertThat(actual).isTrue();
	}

	@DisplayName("월간 퀘스트 개수가 퀘스트 제한에 도달하지 않는다.")
	@Test
	void not_reached_monthly_quest_threshold() {
		QuestProperty questProperty = new QuestProperty(DailyThreshold.of(5), WeeklyThreshold.of(5), MonthlyThreshold.of(5));
		boolean actual = questProperty.hasReachedQuestThreshold(QuestType.MONTHLY.name(), 4);

		assertThat(actual).isFalse();
	}

	@DisplayName("월간 퀘스트 개수가 퀘스트 제한에 도달한다.")
	@Test
	void reached_monthly_quest_threshold() {
		QuestProperty questProperty = new QuestProperty(DailyThreshold.of(5), WeeklyThreshold.of(5), MonthlyThreshold.of(5));
		boolean actual = questProperty.hasReachedQuestThreshold(QuestType.MONTHLY.name(), 5);

		assertThat(actual).isTrue();
	}

	@DisplayName("월간 퀘스트 개수가 퀘스트 제한을 초과한다.")
	@Test
	void exceed_monthly_quest_threshold() {
		QuestProperty questProperty = new QuestProperty(DailyThreshold.of(5), WeeklyThreshold.of(5), MonthlyThreshold.of(5));
		boolean actual = questProperty.hasReachedQuestThreshold(QuestType.MONTHLY.name(), 6);

		assertThat(actual).isTrue();
	}

	@DisplayName("존재하지 않는 퀘스트 타입으로 제한 검증을 한다.")
	@Test
	void validate_threshold_with_invalid_quest_type() {
		QuestProperty questProperty = new QuestProperty(DailyThreshold.of(5), WeeklyThreshold.of(5), MonthlyThreshold.of(5));

		assertThatThrownBy(() -> questProperty.hasReachedQuestThreshold("invalid quest type", 5))
			.isInstanceOf(QuestPropertyConstraintViolationException.class)
			.hasMessageContaining(QuestPropertyErrorCode.UNEXPECTED_QUEST_TYPE.getMessage());
	}
}
