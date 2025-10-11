package com.gomo.app.core.survey.domain.model;

import java.util.UUID;

import com.gomo.app.common.displayorder.DisplayOrder;
import com.gomo.app.common.displayorder.OrderChangeable;
import com.gomo.app.common.jpa.BaseAudit;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class SurveyItem extends BaseAudit implements OrderChangeable {

	@Id
	private UUID id;
	private UUID surveyQuestionId;
	private String content;

	@Embedded
	private DisplayOrder displayOrder;

	protected SurveyItem() {
	}

	private SurveyItem(UUID id, UUID surveyQuestionId, String content, DisplayOrder displayOrder) {
		this.id = id;
		this.surveyQuestionId = surveyQuestionId;
		this.content = content;
		this.displayOrder = displayOrder;
	}

	public static SurveyItem of(UUID id, UUID surveyQuestionId, String content, DisplayOrder displayOrder) {
		return new SurveyItem(id, surveyQuestionId, content, displayOrder);
	}

	@Override
	public void changeOrder(DisplayOrder displayOrder) {
		this.displayOrder = displayOrder;
	}
}
