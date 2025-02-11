package com.gomo.app.survey.presentation.response;

import java.util.List;

import lombok.Getter;

@Getter
public class ListSurveyQuestionResponse {

	private List<ReadSurveyQuestionResponse> surveyQuestions;

	private ListSurveyQuestionResponse(List<ReadSurveyQuestionResponse> surveyQuestions) {
		this.surveyQuestions = surveyQuestions;
	}

	public static ListSurveyQuestionResponse of(List<ReadSurveyQuestionResponse> surveyQuestions) {
		return new ListSurveyQuestionResponse(surveyQuestions);
	}
}
