package com.gomo.app.survey.presentation.response;

import java.util.List;

import lombok.Getter;

@Getter
public class ListSurveyQuestionResponse {

	private List<ReadSurveyQuestionResponse> questions;

	private ListSurveyQuestionResponse(List<ReadSurveyQuestionResponse> questions) {
		this.questions = questions;
	}

	public static ListSurveyQuestionResponse of(List<ReadSurveyQuestionResponse> questions) {
		return new ListSurveyQuestionResponse(questions);
	}
}
