package com.gomo.app.survey.application;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.logging.AuditLog;
import com.gomo.app.survey.domain.model.RespondentId;
import com.gomo.app.survey.domain.model.SurveyItemId;
import com.gomo.app.survey.domain.model.SurveyQuestionId;
import com.gomo.app.survey.domain.model.SurveyResult;
import com.gomo.app.survey.domain.repository.SurveyResultRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class CreateSurveyResultUseCase {

	private final SurveyResultRepository surveyResultRepository;

	@AuditLog(action = "CREATE_SURVEY_RESULT")
	public void create(CreateSurveyResultCommand command) {
		RespondentId targetId = RespondentId.of(command.respondentId());
		List<SurveyResult> surveyResults = command.selectedSurveyItems().stream()
			.map(selectedItem -> createSurveyResult(targetId, selectedItem))
			.toList();
		surveyResultRepository.saveAll(surveyResults);
	}

	@NotNull
	private SurveyResult createSurveyResult(RespondentId respondentId, SurveyItemDto selectedItem) {
		return SurveyResult.of(
			respondentId,
			SurveyQuestionId.of(selectedItem.surveyQuestionId()),
			SurveyItemId.of(selectedItem.id()),
			selectedItem.content(),
			selectedItem.customAnswer()
		);
	}
}
