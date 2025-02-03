package com.gomo.app.quest.domain.model;

import static com.gomo.app.common.exception.DomainErrorCode.*;

import java.util.regex.Pattern;

import com.gomo.app.common.domain.ValueObject;
import com.gomo.app.common.exception.DomainErrorCode;
import com.gomo.app.common.exception.PolicyViolationException;

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
		if(questContent == null || questContent.isBlank()) {
			throw new PolicyViolationException(INVALID_PARAMETER, "Quest content cannot be blank");
		}
	}

	private void ensureValidLength(String questContent) {
		if(questContent.length() < MINIMUM_CONTENT_LENGTH) {
			throw new PolicyViolationException(DomainErrorCode.INVALID_PARAMETER, "Quest content must have at least three characters");
		}

		if(questContent.length() > MAXIMUM_CONTENT_LENGTH) {
			throw new PolicyViolationException(DomainErrorCode.INVALID_PARAMETER, "Quest content must not exceed 30 characters");
		}
	}

	private void ensureNoForbiddenName(String questContent) {
		if(FORBIDDEN_PATTERN.matcher(questContent).find()) {
			throw new PolicyViolationException(INVALID_PARAMETER, "Quest content cannot contain forbidden characters");
		}
	}

	@Override
	public String toString() {
		return this.questContent;
	}
}
