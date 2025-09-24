package com.gomo.app.core.survey.domain.model;

import java.util.UUID;

import com.gomo.app.common.BaseAudit;
import com.gomo.app.common.displayorder.DisplayOrder;
import com.gomo.app.common.displayorder.OrderChangeable;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
public class SurveyItem extends BaseAudit implements OrderChangeable {

	@EmbeddedId
	private SurveyItemId id;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "id", column = @Column(name = "survey_question_id"))
	})
	private SurveyQuestionId surveyQuestionId;
	private String content;

	@Embedded
	private DisplayOrder displayOrder;

	protected SurveyItem() {
	}

	private SurveyItem(
		SurveyItemId id,
		SurveyQuestionId surveyQuestionId,
		String content,
		DisplayOrder displayOrder
	) {
		this.id = id;
		this.surveyQuestionId = surveyQuestionId;
		this.content = content;
		this.displayOrder = displayOrder;
	}

	public static SurveyItem of(
		SurveyItemId id,
		SurveyQuestionId surveyQuestionId,
		String content,
		DisplayOrder displayOrder
	) {
		return new SurveyItem(id, surveyQuestionId, content, displayOrder);
	}

	public UUID id() {
		return id.getId();
	}

	public UUID surveyQuestionId() {
		return surveyQuestionId.getId();
	}

	@Override
	public void changeOrder(DisplayOrder displayOrder) {

	}
}
