package com.gomo.app.member.application.port.dto;

import java.util.UUID;

public record CreateMemberDto(UUID id) {

	public static CreateMemberDto of(UUID id) {
		return new CreateMemberDto(id);
	}
}
