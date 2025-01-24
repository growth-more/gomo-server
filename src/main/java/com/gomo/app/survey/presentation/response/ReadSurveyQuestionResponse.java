package com.gomo.app.survey.presentation.response;

import java.util.List;

import com.gomo.app.survey.domain.model.QuestionSelectType;
import com.gomo.app.survey.domain.model.SurveyQuestionId;

import lombok.Getter;

@Getter
public class ReadSurveyQuestionResponse {

	private SurveyQuestionId surveyQuestionId;
	private QuestionSelectType questionSelectType;
	private boolean isRequired;
	private String content;
	private List<ReadSurveyItemResponse> items;

	private ReadSurveyQuestionResponse(
		SurveyQuestionId surveyQuestionId,
		QuestionSelectType questionSelectType,
		boolean isRequired,
		String content,
		List<ReadSurveyItemResponse> items
	) {
		this.surveyQuestionId = surveyQuestionId;
		this.questionSelectType = questionSelectType;
		this.isRequired = isRequired;
		this.content = content;
		this.items = items;
	}

	public static ReadSurveyQuestionResponse of(
		SurveyQuestionId surveyQuestionId,
		QuestionSelectType questionSelectType,
		boolean isRequired,
		String content,
		List<ReadSurveyItemResponse> items
	) {
		return new ReadSurveyQuestionResponse(surveyQuestionId, questionSelectType, isRequired, content, items);
	}
}
