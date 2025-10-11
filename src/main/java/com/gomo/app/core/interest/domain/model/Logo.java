package com.gomo.app.core.interest.domain.model;

import com.gomo.app.common.arch.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Embeddable
@ValueObject
public class Logo {

	private static final String DEFAULT_IMAGE = "DEFAULT_IMAGE";

	private String url;

	protected Logo() {
	}

	private Logo(String url) {
		this.url = url;
	}

	public static Logo of(String url) {
		if (url == null) {
			return new Logo(DEFAULT_IMAGE);
		}
		return new Logo(url);
	}

	public boolean isDefault() {
		return DEFAULT_IMAGE.equals(this.url);
	}

	public Logo updateUrl(String url) {
		return new Logo(url);
	}

	public Logo delete() {
		return new Logo(DEFAULT_IMAGE);
	}
}
