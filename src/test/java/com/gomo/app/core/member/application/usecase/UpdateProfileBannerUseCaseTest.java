package com.gomo.app.core.member.application.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.gomo.app.core.member.application.port.dto.UpdateProfileBannerDto;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.core.member.fixture.MemberFixture;
import com.gomo.app.support.image.application.port.UploadImagePortIn;

@DisplayName("[Application Unit]: 배너 이미지 수정 기능 테스트")
@ExtendWith(MockitoExtension.class)
public class UpdateProfileBannerUseCaseTest {

	@InjectMocks
	private UpdateProfileBannerUseCase sut;

	@Mock
	private MemberService memberService;

	@Mock
	private UploadImagePortIn uploadImagePortIn;

	private static final String NEW_IMAGE_URL = "https://example.com/profile.jpg";

	@DisplayName("프로필 배너 이미지를 수정 한다.")
	@Test
	void update_profile_banner() {
		Member member = MemberFixture.create();
		MockMultipartFile request = new MockMultipartFile("banner", "mock image data".getBytes());
		UpdateProfileBannerDto expected = UpdateProfileBannerDto.of(NEW_IMAGE_URL);

		doReturn(member).when(memberService).find(member.getId());
		doReturn(Optional.of(NEW_IMAGE_URL)).when(uploadImagePortIn).upload(any(MockMultipartFile.class));

		UpdateProfileBannerDto actual = sut.update(member.getId(), request);

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}
}
