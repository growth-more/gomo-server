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

	public void updatePassword(String originPassword, String updatedPassword, PasswordService passwordService) {
		// TODO <jhl221123>: 사용자가 직접 수정, 비밀번호 분실 후 수정 등을 나눠서 구현할 필요가 있습니다.
		if (originPassword.equals(updatedPassword)) {
			throw new RuntimeException("update password must not same as origin password");
		}
		this.password = this.password.update(originPassword, updatedPassword, passwordService);
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

	public void updateProfileImage(ProfileImage profileImage) {
		this.profileImage = profileImage;
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

	public void login(String inputPassword, PasswordService passwordService) {
		this.password.matches(inputPassword, passwordService);
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
		return new Member(id, email, password, handle, memberName, motto, ProfileImage.createDefault(), ProfileBanner.createDefault(), QuestProperty.createDefault(),
			loginProvider,
			RoleType.ROLE_MEMBER, SubscriptionPlan.FREE, ActivateStatus.ACTIVE, LocalDateTime.now(), null
		);
	}

	public boolean hasReachedQuestThreshold(String questType, int questCount) {
		return this.questProperty.hasReachedQuestThreshold(questType, questCount);
	}
}
