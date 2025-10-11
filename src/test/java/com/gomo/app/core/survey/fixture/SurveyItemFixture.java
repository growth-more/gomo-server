package com.gomo.app.core.survey.fixture;

import java.util.UUID;

import com.gomo.app.common.displayorder.DisplayOrder;
import com.gomo.app.core.survey.domain.model.SurveyItem;

public class SurveyItemFixture {

	public static SurveyItem surveyItem() {
		return SurveyItem.of(
			UUID.randomUUID(),
			UUID.randomUUID(),
			"survey item content",
			DisplayOrder.of(1)
		);
	}

	public static SurveyItem surveyItem(UUID surveyQuestionId, String content, int displayOrder) {
		return SurveyItem.of(
			UUID.randomUUID(),
			surveyQuestionId,
			content,
			DisplayOrder.of(displayOrder)
		);
	}
}
