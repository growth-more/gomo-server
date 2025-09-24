package com.gomo.app.core.member.presentation.response;

import java.util.UUID;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreateMemberResponse {

	private final UUID id;

	private CreateMemberResponse(UUID id) {
		this.id = id;
	}

	public static CreateMemberResponse of(UUID id) {
		return new CreateMemberResponse(id);
	}
}
