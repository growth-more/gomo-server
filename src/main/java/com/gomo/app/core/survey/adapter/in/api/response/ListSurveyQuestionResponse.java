package com.gomo.app.core.survey.adapter.in.api.response;

import java.util.List;

import com.gomo.app.core.survey.application.dto.SurveyQuestionDto;

import lombok.Getter;

@Getter
public class ListSurveyQuestionResponse {

	private List<ReadSurveyQuestionResponse> surveyQuestions;

	private ListSurveyQuestionResponse(List<ReadSurveyQuestionResponse> surveyQuestions) {
		this.surveyQuestions = surveyQuestions;
	}

	public static ListSurveyQuestionResponse from(List<SurveyQuestionDto> surveyQuestions) {
		return new ListSurveyQuestionResponse(surveyQuestions.stream().map(ReadSurveyQuestionResponse::from).toList());
	}
}
