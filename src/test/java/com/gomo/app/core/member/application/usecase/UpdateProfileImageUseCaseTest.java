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

import com.gomo.app.core.member.application.port.dto.UpdateProfileImageDto;
import com.gomo.app.core.member.common.fixture.MemberFixture;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.support.image.port.UploadImagePortIn;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Application Unit]: 프로필 이미지 수정 기능 테스트")
public class UpdateProfileImageUseCaseTest {

	@InjectMocks
	UpdateProfileImageUseCase sut;

	@Mock
	private MemberService memberService;

	@Mock
	private UploadImagePortIn uploadImagePort;

	private static final String NEW_IMAGE_URL = "https://example.com/profile.jpg";

	@DisplayName("프로필 이미지를 수정한다")
	@Test
	void update_profile_image() {
		Member member = MemberFixture.member();
		MockMultipartFile request = new MockMultipartFile("banner", "mock image data".getBytes());
		UpdateProfileImageDto expected = UpdateProfileImageDto.of(NEW_IMAGE_URL);

		doReturn(member).when(memberService).find(member.getId());
		doReturn(NEW_IMAGE_URL).when(uploadImagePort).upload(any(MockMultipartFile.class));

		UpdateProfileImageDto actual = sut.update(member.id(), request);

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}

}
