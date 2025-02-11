package com.gomo.app.survey.common.fixture;

import java.util.UUID;

import com.gomo.app.common.domain.service.DisplayOrder;
import com.gomo.app.survey.domain.model.QuestionSelectType;
import com.gomo.app.survey.domain.model.RespondentId;
import com.gomo.app.survey.domain.model.SurveyItemId;
import com.gomo.app.survey.domain.model.SurveyQuestion;
import com.gomo.app.survey.domain.model.SurveyQuestionId;
import com.gomo.app.survey.domain.model.SurveyResult;

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
