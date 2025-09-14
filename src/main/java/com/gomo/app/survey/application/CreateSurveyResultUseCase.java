package com.gomo.app.survey.application;

import java.util.ArrayList;
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
import com.gomo.app.survey.presentation.request.CreateSurveyResultRequest;
import com.gomo.app.survey.presentation.request.SelectedSurveyItem;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class CreateSurveyResultUseCase {

	private final SurveyResultRepository surveyResultRepository;

	@AuditLog(action = "CREATE_SURVEY_RESULT")
	public void create(RespondentId respondentId, CreateSurveyResultRequest request) {
		List<SurveyResult> surveyResults = new ArrayList<>();
		for (SelectedSurveyItem selectedItem : request.getSurveyResult()) {
			surveyResults.add(createSurveyResult(respondentId, selectedItem));
		}
		surveyResultRepository.saveAll(surveyResults);
	}

	@NotNull
	private SurveyResult createSurveyResult(RespondentId respondentId, SelectedSurveyItem selectedItem) {
		return SurveyResult.of(
			respondentId,
			SurveyQuestionId.of(selectedItem.getSurveyQuestionId()),
			SurveyItemId.of(selectedItem.getSurveyItemId()),
			selectedItem.getSurveyItemContent(),
			selectedItem.getCustomAnswer()
		);
	}
}
