package com.gomo.app.core.survey.fixture;

import java.util.UUID;

import com.gomo.app.common.displayorder.DisplayOrder;
import com.gomo.app.core.survey.domain.model.QuestionSelectType;
import com.gomo.app.core.survey.domain.model.SurveyQuestion;

public class SurveyQuestionFixture {

	public static SurveyQuestion surveyQuestion() {
		return SurveyQuestion.of(
			UUID.randomUUID(),
			QuestionSelectType.SINGLE,
			true,
			"survey question content",
			DisplayOrder.of(1)
		);
	}
}
