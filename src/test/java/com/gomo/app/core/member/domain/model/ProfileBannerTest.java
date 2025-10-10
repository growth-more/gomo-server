package com.gomo.app.core.member.domain.model;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("[Domain unit]: 프로필 배경 테스트")
@ExtendWith(MockitoExtension.class)
public class ProfileBannerTest {

	private Password PASSWORD;

	@BeforeEach
	void setUp() {
		PASSWORD = Password.ofRaw("Test1234!");
	}

	private static final MemberId ID = MemberId.of(UUID.randomUUID());
	private static final Email EMAIL = Email.of("test@gmail.com");
	private static final Handle HANDLE = Handle.of("@tester");
	private static final MemberName MEMBER_NAME = MemberName.of("Tester");
	private static final Motto MOTTO = Motto.of("mottoTest");
	private static final String DEFAULT_IMAGE = "DEFAULT_IMAGE";
	private static final LoginProvider LOGIN_PROVIDER = LoginProvider.EMAIL;

	@DisplayName("프로필 배너를 등록하지 않으면 기본 이미지가 등록된다.")
	@Test
	void create_member_with_default_banner() {
		Member member = Member.of(ID, EMAIL, PASSWORD, HANDLE, MEMBER_NAME, MOTTO, LOGIN_PROVIDER);
		assertThat(member.profileBannerUrl()).isEqualTo(DEFAULT_IMAGE);
	}

	@DisplayName("프로필 배너를 업데이트 한다.")
	@Test
	void update_member_banner() {
		Member member = Member.of(ID, EMAIL, PASSWORD, HANDLE, MEMBER_NAME, MOTTO, LOGIN_PROVIDER);
		member.updateProfileBanner("https://mini-io/updated_banner.png");
		assertThat(member.profileBannerUrl()).isEqualTo("https://mini-io/updated_banner.png");
	}

	@DisplayName("프로필 배너를 삭제하면 기본 이미지로 수정된다.")
	@Test
	void delete_member_banner() {
		Member member = Member.of(ID, EMAIL, PASSWORD, HANDLE, MEMBER_NAME, MOTTO, LOGIN_PROVIDER);
		member.delete();
		assertThat(member.profileImageUrl()).isEqualTo(DEFAULT_IMAGE);
	}
}
