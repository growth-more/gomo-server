package com.gomo.app.member.presentation.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.member.application.port.dto.MemberDto;

import lombok.Getter;

@Getter
public class ReadMemberResponse {

	private UUID id;
	private String email;
	private String handle;
	private String name;
	private String motto;
	private int availablePoints;
	private String profileImageUrl;
	private String profileBannerUrl;
	private String loginProvider;
	private String roleType;
	private String subscriptionPlan;
	private String activateStatus;
	private LocalDateTime signUpDateTime;

	private ReadMemberResponse(
		UUID id,
		String email,
		String handle,
		String name,
		String motto,
		int availablePoints,
		String profileImageUrl,
		String profileBannerUrl,
		String loginProvider,
		String roleType,
		String subscriptionPlan,
		String activateStatus,
		LocalDateTime signUpDateTime
	) {
		this.id = id;
		this.email = email;
		this.handle = handle;
		this.name = name;
		this.motto = motto;
		this.availablePoints = availablePoints;
		this.profileImageUrl = profileImageUrl;
		this.profileBannerUrl = profileBannerUrl;
		this.loginProvider = loginProvider;
		this.roleType = roleType;
		this.subscriptionPlan = subscriptionPlan;
		this.activateStatus = activateStatus;
		this.signUpDateTime = signUpDateTime;
	}

	public static ReadMemberResponse of(MemberDto dto) {
		return new ReadMemberResponse(
			dto.id(),
			dto.email(),
			dto.handle(),
			dto.name(),
			dto.motto(),
			dto.availablePoints(),
			dto.profileImageUrl(),
			dto.profileBannerUrl(),
			dto.loginProvider(),
			dto.roleType(),
			dto.subscriptionPlan(),
			dto.activateStatus(),
			dto.signUpDateTime()
		);
	}
}
