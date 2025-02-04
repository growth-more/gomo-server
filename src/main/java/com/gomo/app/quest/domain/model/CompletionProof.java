package com.gomo.app.quest.domain.model;

import static com.gomo.app.common.exception.DomainErrorCode.*;

import java.util.Objects;

import com.gomo.app.common.domain.ValueObject;
import com.gomo.app.common.exception.PolicyViolationException;

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
		if(url == null || url.isBlank()) {
			throw new PolicyViolationException(INVALID_PARAMETER, "Completion proof cannot be blank");
		}
	}

	private void ensureValidLength(String url) {
		if(url.length() > MAX_LENGTH) {
			throw new PolicyViolationException(INVALID_PARAMETER, "Completion proof must not exceed 512 characters");
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
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
