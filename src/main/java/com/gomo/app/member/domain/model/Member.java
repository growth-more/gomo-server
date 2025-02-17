package com.gomo.app.member.domain.model;

import java.time.LocalDateTime;

import com.gomo.app.common.domain.LogicalDeleteBaseAudit;

import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.exception.MemberAuthenticationFailedException;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

import static com.gomo.app.member.exception.MemberErrorCode.AUTHENTICATION_FAILED;

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
		this.questProperty = questProperty;
		this.loginProvider = loginProvider;
		this.roleType = roleType;
		this.subscriptionPlan = subscriptionPlan;
		this.activateStatus = activateStatus;
		this.signUpDateTime = signUpDateTime;
		this.lastLoginDateTime = lastLoginDateTime;
	}

	public void updatePassword(Password password){
		this.password = password;
	}
	public void updateHandle(Handle handle){this.handle = handle;}
	public void delete(){this.activateStatus = ActivateStatus.DELETED;}
	public void updateMotto(Motto motto){this.motto = motto;}
	public void updateName(MemberName name){this.name = name;}
	public void updateProfileImage(ProfileImage profileImage){this.profileImage = profileImage;}
	public void updateQuestProperty(QuestProperty questProperty){this.questProperty = questProperty;}
	public void updateLastLoginDateTime(LocalDateTime lastLoginDateTime){this.lastLoginDateTime = lastLoginDateTime;}

	public void login(String inputPassword, PasswordService passwordService){
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
		return new Member(id, email, password, handle, memberName, motto, ProfileImage.createDefault(), QuestProperty.createDefault(), loginProvider,
			RoleType.ROLE_MEMBER, SubscriptionPlan.FREE, ActivateStatus.ACTIVE, LocalDateTime.now(), null
		);
	}

	public boolean hasReachedQuestThreshold(String questType, int questCount) {
		return switch (questType) {
			case "DAILY" -> questProperty.getDailyThreshold().getThreshold() <= questCount;
			case "WEEKLY" -> questProperty.getWeeklyThreshold().getThreshold() <= questCount;
			case "MONTHLY" -> questProperty.getMonthlyThreshold().getThreshold() <= questCount;
			default -> throw new IllegalArgumentException("Invalid quest type: " + questType);
		};
	}
}
