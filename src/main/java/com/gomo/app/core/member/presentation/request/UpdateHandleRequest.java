package com.gomo.app.core.member.presentation.request;

import lombok.Getter;

@Getter
public class UpdateHandleRequest {

	private final String handle;

	private UpdateHandleRequest(String handle) {
		this.handle = handle;
	}

	public static UpdateHandleRequest of(String handle) {
		return new UpdateHandleRequest(handle);
	}
}
