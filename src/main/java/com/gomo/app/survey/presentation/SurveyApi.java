package com.gomo.app.survey.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.survey.application.CreateSurveyResultUseCase;
import com.gomo.app.survey.application.ReadSurveyQuestionUseCase;
import com.gomo.app.survey.presentation.request.CreateSurveyResultRequest;
import com.gomo.app.survey.presentation.response.ListSurveyQuestionResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/surveys")
@Presentation
public class SurveyApi {

	private final CreateSurveyResultUseCase createSurveyResultUseCase;
	private final ReadSurveyQuestionUseCase readSurveyQuestionUseCase;

	@PostMapping
	public ResponseEntity<Void> createSurveyResult(@RequestBody CreateSurveyResultRequest request) {
		return null;
	}

	@GetMapping
	public ResponseEntity<ListSurveyQuestionResponse> findSurveyQuestions() {
		return null;
	}
}
