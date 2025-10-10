package com.gomo.app.core.survey.fixture;

import java.util.UUID;

import com.gomo.app.common.displayorder.DisplayOrder;
import com.gomo.app.core.survey.domain.model.SurveyItem;
import com.gomo.app.core.survey.domain.model.SurveyItemId;
import com.gomo.app.core.survey.domain.model.SurveyQuestionId;

public class SurveyItemFixture {

	public static SurveyItem surveyItem() {
		return SurveyItem.of(
			SurveyItemId.of(UUID.randomUUID()),
			SurveyQuestionId.of(UUID.randomUUID()),
			"survey item content",
			DisplayOrder.of(1)
		);
	}

	public static SurveyItem surveyItem(UUID surveyQuestionId, String content, int displayOrder) {
		return SurveyItem.of(
			SurveyItemId.of(UUID.randomUUID()),
			SurveyQuestionId.of(surveyQuestionId),
			content,
			DisplayOrder.of(displayOrder)
		);
	}
}
