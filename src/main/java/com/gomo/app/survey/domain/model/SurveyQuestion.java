package com.gomo.app.survey.domain.model;

import com.gomo.app.common.domain.BaseAudit;
import com.gomo.app.common.domain.service.DisplayOrder;
import com.gomo.app.common.domain.service.OrderChangeable;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
@Entity
public class SurveyQuestion extends BaseAudit implements OrderChangeable {

	@EmbeddedId
	private SurveyQuestionId id;

	@Enumerated(value = EnumType.STRING)
	private QuestionSelectType questionSelectType;
	private boolean isRequired;
	private String content;
	private DisplayOrder displayOrder;

	protected SurveyQuestion() {}

	private SurveyQuestion(
		SurveyQuestionId id,
		QuestionSelectType questionSelectType,
		boolean isRequired,
		String content,
		DisplayOrder displayOrder
	) {
		this.id = id;
		this.questionSelectType = questionSelectType;
		this.isRequired = isRequired;
		this.content = content;
		this.displayOrder = displayOrder;
	}

	public static SurveyQuestion of(
		SurveyQuestionId surveyQuestionId,
		QuestionSelectType questionSelectType,
		boolean isRequired,
		String content,
		DisplayOrder displayOrder
	) {
		return new SurveyQuestion(surveyQuestionId, questionSelectType, isRequired, content, displayOrder);
	}

	@Override
	public void changeOrder(DisplayOrder displayOrder) {}
}
