package com.gomo.app.member.presentation.request;

import lombok.Getter;

@Getter
public class UpdateHandleRequest {

	private String handle;

	private UpdateHandleRequest(String handle) {
		this.handle = handle;
	}

	public static UpdateHandleRequest of(String handle) {
		return new UpdateHandleRequest(handle);
	}
}
