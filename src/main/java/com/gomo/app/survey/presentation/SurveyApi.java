package com.gomo.app.survey.presentation;

import static org.springframework.http.HttpStatus.*;

import com.gomo.app.common.authentication.Auth;
import com.gomo.app.member.domain.model.MemberId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.authentication.MemberContext;
import com.gomo.app.common.authentication.SessionMember;
import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.survey.application.CreateSurveyResultUseCase;
import com.gomo.app.survey.application.ReadSurveyQuestionUseCase;
import com.gomo.app.survey.domain.model.RespondentId;
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
	public ResponseEntity<Void> createSurveyResult(@Auth MemberId memberId, @RequestBody CreateSurveyResultRequest request) {
		createSurveyResultUseCase.create(RespondentId.of(memberId.getId()), request);
		return ResponseEntity.status(CREATED).build();
	}

	@GetMapping
	public ResponseEntity<ListSurveyQuestionResponse> findSurveyQuestions() {
		ListSurveyQuestionResponse response = readSurveyQuestionUseCase.findAll();
		return ResponseEntity.status(OK).body(response);
	}
}
