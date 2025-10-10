package com.gomo.app.core.survey.domain.model;

import java.util.UUID;

import com.gomo.app.common.displayorder.DisplayOrder;
import com.gomo.app.common.displayorder.OrderChangeable;
import com.gomo.app.common.jpa.BaseAudit;

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

	protected SurveyQuestion() {
	}

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

	public UUID id() {
		return id.getId();
	}

	@Override
	public void changeOrder(DisplayOrder displayOrder) {
	}
}
