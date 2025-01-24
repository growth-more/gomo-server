package com.gomo.app.survey.domain.model;

import com.gomo.app.common.domain.ValueObject;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import lombok.Getter;

@Getter
@ValueObject
public class SurveyResult {

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "uuid", column = @Column(name = "member_id"))
	})
	private RespondentId respondentId;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "uuid", column = @Column(name = "survey_question_id"))
	})
	private SurveyQuestionId surveyQuestionId;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "uuid", column = @Column(name = "survey_item_id"))
	})
	private SurveyItemId surveyItemId;

	private String text;

	protected SurveyResult() {}

	private SurveyResult(
		RespondentId respondentId,
		SurveyQuestionId surveyQuestionId,
		SurveyItemId surveyItemId,
		String text
	) {
		this.respondentId = respondentId;
		this.surveyQuestionId = surveyQuestionId;
		this.surveyItemId = surveyItemId;
		this.text = text;
	}

	public static SurveyResult of(
		RespondentId respondentId,
		SurveyQuestionId surveyQuestionId,
		SurveyItemId surveyItemId,
		String text
	) {
		return new SurveyResult(respondentId, surveyQuestionId, surveyItemId, text);
	}
}
