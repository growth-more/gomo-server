package com.gomo.app.core.survey.fixture;

import java.util.UUID;

import com.gomo.app.common.displayorder.DisplayOrder;
import com.gomo.app.core.survey.domain.model.QuestionSelectType;
import com.gomo.app.core.survey.domain.model.SurveyQuestion;
import com.gomo.app.core.survey.domain.model.SurveyQuestionId;

public class SurveyQuestionFixture {

	public static SurveyQuestion surveyQuestion() {
		return SurveyQuestion.of(
			SurveyQuestionId.of(UUID.randomUUID()),
			QuestionSelectType.SINGLE,
			true,
			"survey question content",
			DisplayOrder.of(1)
		);
	}
}
