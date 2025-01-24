package com.gomo.app.quest.domain.model;

import com.gomo.app.common.domain.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class CompletionProof {

	private static final String BLANK_URL = null;

	private String url;

	protected CompletionProof() {
	}

	private CompletionProof(
		String url
	) {
		this.url = url;
	}

	public static CompletionProof of(
		String url
	) {
		return new CompletionProof(url);
	}

	public static CompletionProof blank() {
		return new CompletionProof(BLANK_URL);
	}
}
