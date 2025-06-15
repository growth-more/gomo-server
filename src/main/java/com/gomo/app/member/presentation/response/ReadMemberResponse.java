package com.gomo.app.member.presentation.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.member.domain.model.ActivateStatus;
import com.gomo.app.member.domain.model.LoginProvider;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.RoleType;
import com.gomo.app.member.domain.model.SubscriptionPlan;

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
	private LoginProvider loginProvider;
	private RoleType roleType;
	private SubscriptionPlan subscriptionPlan;
	private ActivateStatus activateStatus;
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
		LoginProvider loginProvider,
		RoleType roleType,
		SubscriptionPlan subscriptionPlan,
		ActivateStatus activateStatus,
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

	public static ReadMemberResponse of(Member member, int availablePoints) {
		return new ReadMemberResponse(
			member.getId().getId(),
			member.getEmail().toString(),
			member.getHandle().toString(),
			member.getName().toString(),
			member.getMotto().toString(),
			availablePoints,
			member.getProfileImage().getUrl(),
			member.getProfileBanner().getUrl(),
			member.getLoginProvider(),
			member.getRoleType(),
			member.getSubscriptionPlan(),
			member.getActivateStatus(),
			member.getSignUpDateTime()
		);
	}
}
