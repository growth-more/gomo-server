package com.gomo.app.quest.domain.model;

import com.gomo.app.common.domain.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class QuestContent {

	private static final int MINIMUM_CONTENT_LENGTH = 3;
	private static final int MAXIMUM_CONTENT_LENGTH = 30;

	private String questContent;

	protected QuestContent() {
	}

	private QuestContent(String questContent) {
		this.questContent = questContent;
	}

	public static QuestContent of(String questContent) {
		return new QuestContent(questContent);
	}

	public QuestContent update(String questContent) {
		return QuestContent.of(questContent);
	}

	private boolean isValidLength() {
		return questContent.length() >= MINIMUM_CONTENT_LENGTH && questContent.length() <= MAXIMUM_CONTENT_LENGTH;
	}

	private boolean doesContainProhibitCharacters() {
		return false;
	}

	@Override
	public String toString() {
		return this.questContent;
	}
}
