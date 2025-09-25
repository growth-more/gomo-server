package com.gomo.app.core.member.application.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.gomo.app.core.interest.application.port.UploadLogoPortOut;
import com.gomo.app.core.interest.application.port.dto.LogoDto;
import com.gomo.app.core.member.application.port.dto.UpdateProfileBannerDto;
import com.gomo.app.core.member.common.fixture.MemberFixture;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.service.MemberService;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Application Unit]: 배너 이미지 수정 기능 테스트")
public class UpdateProfileBannerUseCaseTest {

	@InjectMocks
	UpdateProfileBannerUseCase sut;

	@Mock
	MemberService memberService;

	@Mock
	UploadLogoPortOut uploadLogoPortOut;

	private static final String NEW_IMAGE_URL = "https://example.com/profile.jpg";

	@DisplayName("프로필 배너 이미지를 수정 한다.")
	@Test
	void update_profile_banner() {
		Member member = MemberFixture.member();
		MockMultipartFile request = new MockMultipartFile("banner", "mock image data".getBytes());
		UpdateProfileBannerDto expected = UpdateProfileBannerDto.of(NEW_IMAGE_URL);

		doReturn(member).when(memberService).find(member.getId());
		doReturn(LogoDto.of(NEW_IMAGE_URL)).when(uploadLogoPortOut).upload(any(MockMultipartFile.class));

		UpdateProfileBannerDto actual = sut.update(member.uuid(), request);

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}
}
