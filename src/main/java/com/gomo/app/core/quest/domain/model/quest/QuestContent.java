package com.gomo.app.core.quest.domain.model.quest;

import java.util.regex.Pattern;

import com.gomo.app.common.arch.ValueObject;
import com.gomo.app.core.quest.exception.QuestContentConstraintViolationException;
import com.gomo.app.core.quest.exception.code.QuestContentErrorCode;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class QuestContent {

	private static final int MINIMUM_CONTENT_LENGTH = 3;
	private static final int MAXIMUM_CONTENT_LENGTH = 30;
	private static final Pattern FORBIDDEN_PATTERN = Pattern.compile("[<>&\"';|\\\\{}\\[\\]()`]|(--|/\\*|\\*/)|[\u0000-\u001F\u007F]");

	private String questContent;

	protected QuestContent() {
	}

	private QuestContent(String questContent) {
		ensureNotBlank(questContent);
		ensureValidLength(questContent);
		ensureNoForbiddenName(questContent);
		this.questContent = questContent;
	}

	public static QuestContent of(String questContent) {
		return new QuestContent(questContent);
	}

	public QuestContent update(String questContent) {
		return QuestContent.of(questContent);
	}

	private void ensureNotBlank(String questContent) {
		if (questContent == null || questContent.isBlank()) {
			throw new QuestContentConstraintViolationException(QuestContentErrorCode.BLANK);
		}
	}

	private void ensureValidLength(String questContent) {
		if (questContent.length() < MINIMUM_CONTENT_LENGTH) {
			throw new QuestContentConstraintViolationException(QuestContentErrorCode.TOO_SHORT);
		}

		if (questContent.length() > MAXIMUM_CONTENT_LENGTH) {
			throw new QuestContentConstraintViolationException(QuestContentErrorCode.TOO_LONG);
		}
	}

	private void ensureNoForbiddenName(String questContent) {
		if (FORBIDDEN_PATTERN.matcher(questContent).find()) {
			throw new QuestContentConstraintViolationException(QuestContentErrorCode.FORBIDDEN);
		}
	}

	@Override
	public String toString() {
		return this.questContent;
	}
}
