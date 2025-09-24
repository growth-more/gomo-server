package com.gomo.app.core.survey.domain.model;

import java.util.regex.Pattern;

import com.gomo.app.common.ValueObject;
import com.gomo.app.core.survey.exception.SurveyResultConstraintViolationException;
import com.gomo.app.core.survey.exception.SurveyResultErrorCode;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import lombok.Getter;

@Getter
@ValueObject
public class SurveyResult {

	private static final String OTHER = "기타";
	private static final int MAX_LENGTH = 20;
	private static final Pattern FORBIDDEN_PATTERN = Pattern.compile("[<>&\"';|\\\\{}\\[\\]()`]|(--|/\\*|\\*/)|[\u0000-\u001F\u007F]");

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "id", column = @Column(name = "respondent_id"))
	})
	private RespondentId respondentId;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "id", column = @Column(name = "survey_question_id"))
	})
	private SurveyQuestionId surveyQuestionId;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "id", column = @Column(name = "survey_item_id"))
	})
	private SurveyItemId surveyItemId;
	private String surveyItemContent;
	private String customAnswer;

	protected SurveyResult() {}

	private SurveyResult(
		RespondentId respondentId,
		SurveyQuestionId surveyQuestionId,
		SurveyItemId surveyItemId,
		String surveyItemContent,
		String customAnswer
	) {
		ensureValidCustomAnswer(surveyItemContent, customAnswer);
		this.respondentId = respondentId;
		this.surveyQuestionId = surveyQuestionId;
		this.surveyItemId = surveyItemId;
		this.surveyItemContent = surveyItemContent;
		this.customAnswer = customAnswer;
	}

	public static SurveyResult of(
		RespondentId respondentId,
		SurveyQuestionId surveyQuestionId,
		SurveyItemId surveyItemId,
		String surveyItemContent,
		String customAnswer
	) {
		return new SurveyResult(respondentId, surveyQuestionId, surveyItemId, surveyItemContent, customAnswer);
	}

	private void ensureValidCustomAnswer(String surveyItemContent, String customAnswer) {
		ensureNoExistCustomAnswerIfOtherNotSelected(surveyItemContent, customAnswer);
		ensureExistValidCustomAnswerIfOtherSelected(surveyItemContent, customAnswer);
	}

	private void ensureNoExistCustomAnswerIfOtherNotSelected(String surveyItemContent, String customAnswer) {
		if(!OTHER.equals(surveyItemContent) && customAnswer != null) {
			throw new SurveyResultConstraintViolationException(SurveyResultErrorCode.UNEXPECTED_CUSTOM_ANSWER);
		}
	}

	private void ensureExistValidCustomAnswerIfOtherSelected(String surveyItemContent, String customAnswer) {
		if(OTHER.equals(surveyItemContent)) {
			ensureNotBlank(customAnswer);
			ensureValidLength(customAnswer);
			ensureNoForbiddenName(customAnswer);
		}
	}

	private static void ensureNotBlank(String customAnswer) {
		if (customAnswer == null || customAnswer.isBlank()) {
			throw new SurveyResultConstraintViolationException(SurveyResultErrorCode.MISSING_CUSTOM_ANSWER);
		}
	}

	private void ensureValidLength(String customAnswer) {
		if(customAnswer.length() > MAX_LENGTH) {
			throw new SurveyResultConstraintViolationException(SurveyResultErrorCode.TOO_LONG);
		}
	}

	private void ensureNoForbiddenName(String customAnswer) {
		if(FORBIDDEN_PATTERN.matcher(customAnswer).find()) {
			throw new SurveyResultConstraintViolationException(SurveyResultErrorCode.FORBIDDEN);
		}
	}
}
