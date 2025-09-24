package com.gomo.app.core.survey.presentation;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.Presentation;
import com.gomo.app.support.auth.Auth;
import com.gomo.app.support.auth.AuthInfo;
import com.gomo.app.core.survey.application.CreateSurveyResultUseCase;
import com.gomo.app.core.survey.application.ReadSurveyQuestionUseCase;
import com.gomo.app.core.survey.application.SurveyQuestionDto;
import com.gomo.app.core.survey.presentation.request.CreateSurveyResultRequest;
import com.gomo.app.core.survey.presentation.response.ListSurveyQuestionResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/surveys")
@Presentation
public class SurveyApi {

	private final CreateSurveyResultUseCase createSurveyResultUseCase;
	private final ReadSurveyQuestionUseCase readSurveyQuestionUseCase;

	@PostMapping
	public ResponseEntity<Void> createSurveyResult(@Auth AuthInfo authInfo, @RequestBody CreateSurveyResultRequest request) {
		createSurveyResultUseCase.create(request.toCommand(authInfo.getMemberId()));
		return ResponseEntity.status(CREATED).build();
	}

	@GetMapping
	public ResponseEntity<ListSurveyQuestionResponse> findSurveyQuestions() {
		List<SurveyQuestionDto> dto = readSurveyQuestionUseCase.findAll();
		return ResponseEntity.status(OK).body(ListSurveyQuestionResponse.from(dto));
	}
}
