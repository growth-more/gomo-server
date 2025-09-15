package com.gomo.app.member.presentation.response;

import java.util.UUID;

import com.gomo.app.member.domain.model.MemberId;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreateMemberResponse {

	private UUID id;

	private CreateMemberResponse(UUID id) {
		this.id = id;
	}

	public static CreateMemberResponse of(MemberId memberId) {
		return new CreateMemberResponse(memberId.getId());
	}
}
