package com.gomo.app.survey.domain.model;

import com.gomo.app.common.BaseAudit;
import com.gomo.app.displayorder.DisplayOrder;
import com.gomo.app.displayorder.OrderChangeable;

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

	protected SurveyItem() {}

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

	@Override
	public void changeOrder(DisplayOrder displayOrder) {

	}
}
