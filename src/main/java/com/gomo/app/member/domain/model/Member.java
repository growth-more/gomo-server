package com.gomo.app.member.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.common.LogicalDeleteBaseAudit;
import com.gomo.app.member.domain.service.PasswordService;

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
	@AttributeOverrides({
		@AttributeOverride(name = "url", column = @Column(name = "profile_banner_url")),
	})
	private ProfileBanner profileBanner;

	// TODO <jhl221123>: 자동 생성되는 퀘스트와 수동 생성하는 퀘스트 수 제한을 분리해야 합니다. 구독 플랜을 활용해 시스템 제한을 정하고 제한 내에서 자동 생성을 조정하도록 해야합니다.
	@Embedded
	private QuestProperty questProperty;

	@Enumerated(EnumType.STRING)
	private LoginProvider loginProvider;

	@Enumerated(EnumType.STRING)
	private RoleType roleType;

	@Enumerated(EnumType.STRING)
	private SubscriptionPlan subscriptionPlan;

	@Enumerated(EnumType.STRING)
	private ActivateStatus activateStatus;
	private LocalDateTime signUpDateTime;
	private LocalDateTime lastLoginDateTime;

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
		ProfileBanner profileBanner,
		QuestProperty questProperty,
		LoginProvider loginProvider,
		RoleType roleType,
		SubscriptionPlan subscriptionPlan,
		ActivateStatus activateStatus,
		LocalDateTime signUpDateTime,
		LocalDateTime lastLoginDateTime
	) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.handle = handle;
		this.name = name;
		this.motto = motto;
		this.profileImage = profileImage;
		this.profileBanner = profileBanner;
		this.questProperty = questProperty;
		this.loginProvider = loginProvider;
		this.roleType = roleType;
		this.subscriptionPlan = subscriptionPlan;
		this.activateStatus = activateStatus;
		this.signUpDateTime = signUpDateTime;
		this.lastLoginDateTime = lastLoginDateTime;
	}

	public UUID uuid() {
		return this.id.getId();
	}

	public void updatePassword(Password rawOld, Password rawNew, PasswordService passwordService) {
		if (rawOld.getPassword().equals(rawNew.getPassword())) {
			throw new RuntimeException("update password must not same as origin password");
		}
		this.password = rawNew.encodedWith(passwordService);
	}

	public void resetPassword(PasswordService passwordService, Password newPassword) {
		this.password = newPassword.encodedWith(passwordService);
	}

	public void updateHandle(String handle) {
		this.handle = this.handle.update(handle);
	}

	public void delete() {
		this.activateStatus = ActivateStatus.DELETED;
	}

	public void deleteProfile() {
		this.profileImage = this.profileImage.delete();
	}

	public void deleteBanner() {
		this.profileBanner = this.profileBanner.delete();
	}

	public void updateMotto(String motto) {
		this.motto = this.motto.update(motto);
	}

	public void updateName(String name) {
		this.name = this.name.update(name);
	}

	public void updateMemberInfo(String name, String motto) {
		updateMotto(motto);
		updateName(name);
	}

	public void updateProfileImage(String updatedUrl) {
		this.profileImage = this.profileImage.updateUrl(updatedUrl);
	}

	public void updateProfileBanner(String updatedUrl) {
		this.profileBanner = this.profileBanner.updateUrl(updatedUrl);
	}

	public void updateQuestProperty(QuestProperty questProperty) {
		this.questProperty = questProperty;
	}

	public void updateLastLoginDateTime(LocalDateTime lastLoginDateTime) {
		this.lastLoginDateTime = lastLoginDateTime;
	}

	public void login(PasswordService passwordService, Password inputPassword) {
		this.password.verifyWith(passwordService, inputPassword);
	}

	public static Member of(
		MemberId id,
		Email email,
		Password password,
		Handle handle,
		MemberName memberName,
		Motto motto,
		LoginProvider loginProvider
	) {
		return new Member(id, email, password, handle, memberName, motto, ProfileImage.createDefault(),
			ProfileBanner.createDefault(), QuestProperty.createDefault(),
			loginProvider,
			RoleType.ROLE_MEMBER, SubscriptionPlan.FREE, ActivateStatus.ACTIVE, LocalDateTime.now(), null
		);
	}
}
