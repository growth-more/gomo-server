package com.gomo.app.survey.presentation.response;

import java.util.List;
import java.util.UUID;

import com.gomo.app.survey.domain.model.QuestionSelectType;
import com.gomo.app.survey.domain.model.SurveyQuestion;

import lombok.Getter;

@Getter
public class ReadSurveyQuestionResponse {

	private UUID id;
	private QuestionSelectType questionSelectType;
	private boolean isRequired;
	private String content;
	private List<ReadSurveyItemResponse> surveyItems;

	private ReadSurveyQuestionResponse(
		UUID id,
		QuestionSelectType questionSelectType,
		boolean isRequired,
		String content,
		List<ReadSurveyItemResponse> surveyItems
	) {
		this.id = id;
		this.questionSelectType = questionSelectType;
		this.isRequired = isRequired;
		this.content = content;
		this.surveyItems = surveyItems;
	}

	public static ReadSurveyQuestionResponse of(
		SurveyQuestion surveyQuestion,
		List<ReadSurveyItemResponse> surveyItems
	) {
		return new ReadSurveyQuestionResponse(
			surveyQuestion.getId().getId(),
			surveyQuestion.getQuestionSelectType(),
			surveyQuestion.isRequired(),
			surveyQuestion.getContent(),
			surveyItems
		);
	}
}
