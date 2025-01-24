package com.gomo.app.member.domain.model;

import java.time.LocalDateTime;

import com.gomo.app.common.domain.LogicalDeleteBaseAudit;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
@Entity
public class Member extends LogicalDeleteBaseAudit {

	@EmbeddedId
	private MemberId id;

	@Embedded
	private Email email;

	@Embedded
	private Password password;

	@Embedded
	private Handle handle;

	@Embedded
	private MemberName name;

	@Embedded
	private Motto motto;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "url", column = @Column(name = "profile_image_url")),
		@AttributeOverride(name = "originName", column = @Column(name = "profile_image_origin_name"))
	})
	private ProfileImage profileImage;

	@Embedded
	private QuestProperty questProperty;

	@Enumerated(EnumType.STRING)
	private RoleType roleType;

	@Enumerated(EnumType.STRING)
	private SubscriptionPlan subscriptionPlan;

	@Enumerated(EnumType.STRING)
	private ActivateStatus activateStatus;
	private LocalDateTime signUpDateTime;

	protected Member() {
	}

	public Member(
		MemberId id,
		Email email,
		Password password,
		Handle handle,
		MemberName name,
		Motto motto,
		ProfileImage profileImage,
		QuestProperty questProperty,
		RoleType roleType,
		SubscriptionPlan subscriptionPlan,
		ActivateStatus activateStatus,
		LocalDateTime signUpDateTime
	) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.handle = handle;
		this.name = name;
		this.motto = motto;
		this.profileImage = profileImage;
		this.questProperty = questProperty;
		this.roleType = roleType;
		this.subscriptionPlan = subscriptionPlan;
		this.activateStatus = activateStatus;
		this.signUpDateTime = signUpDateTime;
	}

	public static Member of(
		MemberId id,
		Email email,
		Password password,
		Handle handle,
		MemberName memberName,
		Motto motto
	) {
		return new Member(id, email, password, handle, memberName, motto, ProfileImage.createDefault(), QuestProperty.createDefault(),
			RoleType.ROLE_MEMBER, SubscriptionPlan.FREE, ActivateStatus.ACTIVE, LocalDateTime.now()
		);
	}
}
