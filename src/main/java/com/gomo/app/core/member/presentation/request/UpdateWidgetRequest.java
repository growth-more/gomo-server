package com.gomo.app.core.member.presentation.request;

import lombok.Getter;

@Getter
public class UpdateWidgetRequest {

	private final String snapshot;

	private UpdateWidgetRequest(String snapshot) {
		this.snapshot = snapshot;
	}

	public static UpdateWidgetRequest of(String snapshot) {
		return new UpdateWidgetRequest(snapshot);
	}
}
