package com.gomo.app.core.survey.adapter.in.api;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.core.survey.adapter.in.api.request.CreateSurveyResultRequest;
import com.gomo.app.core.survey.adapter.in.api.response.ListSurveyQuestionResponse;
import com.gomo.app.core.survey.application.dto.SurveyQuestionDto;
import com.gomo.app.core.survey.application.service.SurveyQuestionService;
import com.gomo.app.core.survey.application.service.SurveyResultService;
import com.gomo.app.support.auth.adapter.in.security.Auth;
import com.gomo.app.support.auth.adapter.in.security.AuthInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/surveys")
@CoreApi
public class SurveyApi {

	private final SurveyResultService surveyResultService;
	private final SurveyQuestionService surveyQuestionService;

	@PostMapping
	public ResponseEntity<Void> createSurveyResult(@Auth AuthInfo authInfo, @RequestBody CreateSurveyResultRequest request) {
		surveyResultService.create(request.toCommand(authInfo.getPrincipalId()));
		return ResponseEntity.status(CREATED).build();
	}

	@GetMapping
	public ResponseEntity<ListSurveyQuestionResponse> findSurveyQuestions() {
		List<SurveyQuestionDto> dto = surveyQuestionService.findAll();
		return ResponseEntity.status(OK).body(ListSurveyQuestionResponse.from(dto));
	}
}
