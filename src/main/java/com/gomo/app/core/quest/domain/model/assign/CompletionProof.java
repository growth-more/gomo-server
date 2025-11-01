package com.gomo.app.core.quest.domain.model.assign;

import java.util.Objects;

import com.gomo.app.common.arch.ValueObject;
import com.gomo.app.core.quest.domain.exception.CompletionProofConstraintViolationException;
import com.gomo.app.core.quest.domain.exception.code.CompletionProofErrorCode;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class CompletionProof {

	private static final int MAX_LENGTH = 512;
	private static final String NO_PROOF = "no_proof";

	private String url;

	protected CompletionProof() {
	}

	private CompletionProof(String url) {
		ensureNotBlank(url);
		ensureValidLength(url);
		this.url = url;
	}

	public static CompletionProof createDefault() {
		return new CompletionProof(NO_PROOF);
	}

	public static CompletionProof of(String url) {
		return new CompletionProof(url);
	}

	private void ensureNotBlank(String url) {
		if (url == null || url.isBlank()) {
			throw new CompletionProofConstraintViolationException(CompletionProofErrorCode.BLANK);
		}
	}

	private void ensureValidLength(String url) {
		if (url.length() > MAX_LENGTH) {
			throw new CompletionProofConstraintViolationException(CompletionProofErrorCode.TOO_LONG);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		CompletionProof completionProof = (CompletionProof)o;
		return Objects.equals(url, completionProof.url);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(url);
	}

	@Override
	public String toString() {
		return this.url;
	}
}
