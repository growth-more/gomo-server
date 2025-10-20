package com.gomo.app.core.survey.application;

import java.util.List;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.survey.domain.model.SurveyResult;
import com.gomo.app.core.survey.domain.repository.SurveyResultRepository;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
public class CreateSurveyResultUseCase {

	private final SurveyResultRepository surveyResultRepository;

	@AuditLog(action = "CREATE_SURVEY_RESULT")
	public void create(CreateSurveyResultCommand command) {
		UUID respondentId = command.respondentId();
		List<SurveyResult> surveyResults = command.selectedSurveyItems().stream()
			.map(selectedItem -> createSurveyResult(respondentId, selectedItem))
			.toList();
		surveyResultRepository.saveAll(surveyResults);
	}

	@NotNull
	private SurveyResult createSurveyResult(UUID respondentId, SurveyItemDto selectedItem) {
		return SurveyResult.of(
			respondentId,
			selectedItem.surveyQuestionId(),
			selectedItem.id(),
			selectedItem.content(),
			selectedItem.customAnswer()
		);
	}
}
