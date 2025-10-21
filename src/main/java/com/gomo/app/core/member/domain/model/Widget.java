package com.gomo.app.core.member.domain.model;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import com.gomo.app.common.arch.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class Widget {

	private static final String DEFAULT_JSON_PATH = "/default-widget.json";
	private static final String DEFAULT_WIDGET;

	private String snapshot;

	static {
		String json;
		try (InputStream is = Widget.class.getResourceAsStream(DEFAULT_JSON_PATH)) {
			Objects.requireNonNull(is, "default-widget.json not found in resources");
			json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException("Failed to load default widget JSON", e);
		}
		DEFAULT_WIDGET = json;
	}

	protected Widget() {
	}

	private Widget(String snapshot) {
		this.snapshot = snapshot;
	}

	public static Widget createDefault() {
		return new Widget(DEFAULT_WIDGET);
	}

	public Widget update(String snapshot) {
		return new Widget(snapshot);
	}
}
