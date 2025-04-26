package com.gomo.app.survey.common.fixture;

import java.util.UUID;

import com.gomo.app.displayorder.DisplayOrder;
import com.gomo.app.survey.domain.model.SurveyItem;
import com.gomo.app.survey.domain.model.SurveyItemId;
import com.gomo.app.survey.domain.model.SurveyQuestionId;

public class SurveyItemFixture {

	public static SurveyItem surveyItem() {
		return SurveyItem.of(
			SurveyItemId.of(UUID.randomUUID()),
			SurveyQuestionId.of(UUID.randomUUID()),
			"survey item content",
			DisplayOrder.of(1)
		);
	}
}
