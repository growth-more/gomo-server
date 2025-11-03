package com.gomo.app.core.member.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.gomo.app.core.member.application.port.out.ProfileAssetUploader;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.fixture.MemberFixture;

@DisplayName("[Application Unit]: 프로필 이미지 서비스 기능 테스트")
@ExtendWith(MockitoExtension.class)
public class TempProfileImageServiceTest {

	@InjectMocks
	private ProfileImageService sut;

	@Mock
	private MemberService memberService;

	@Mock
	private ProfileAssetUploader profileAssetUploader;

	private static final String NEW_IMAGE_URL = "https://example.com/profile.jpg";

	@DisplayName("프로필 이미지를 수정한다")
	@Test
	void update_profile_image() {
		Member member = MemberFixture.create();
		MockMultipartFile request = new MockMultipartFile("banner", "mock image data".getBytes());

		doReturn(member).when(memberService).findById(member.getId());
		doReturn(NEW_IMAGE_URL).when(profileAssetUploader).upload(any(MockMultipartFile.class));

		sut.update(member.getId(), request);

		assertThat(member.getProfileImage().getUrl()).isEqualTo(NEW_IMAGE_URL);
	}

	@DisplayName("프로필 이미지를 삭제한다.")
	@Test
	void delete_profile_image() {
		Member member = MemberFixture.create();
		doReturn(member).when(memberService).findById(member.getId());

		sut.delete(member.getId());

		assertThat(member.profileImageUrl()).isEqualTo("DEFAULT_IMAGE");
	}
}
