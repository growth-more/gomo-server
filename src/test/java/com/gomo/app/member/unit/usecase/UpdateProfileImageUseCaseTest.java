package com.gomo.app.member.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.gomo.app.image.ImageService;
import com.gomo.app.member.application.UpdateProfileImageUseCase;
import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.member.presentation.response.UpdateProfileImageResponse;

@DisplayName("[Application Unit]: 프로필 이미지 수정 기능 테스트")
@ExtendWith(MockitoExtension.class)
public class UpdateProfileImageUseCaseTest {

    @InjectMocks
    private UpdateProfileImageUseCase sut;

    @Mock
    private MemberService memberService;

    @Mock
    private ImageService imageService;

    private static final String NEW_IMAGE_URL = "https://example.com/profile.jpg";

    @DisplayName("프로필 이미지를 업데이트 한다.")
    @Test
    void update_member_profile(){
        Member member = MemberFixture.member();
        MockMultipartFile request = new MockMultipartFile("profile", "mock image data".getBytes());
        UpdateProfileImageResponse expected = UpdateProfileImageResponse.of(NEW_IMAGE_URL);

        doReturn(member).when(memberService).find(MemberId.of(member.uuid()));
        doReturn(NEW_IMAGE_URL).when(imageService).uploadImage(any());

        UpdateProfileImageResponse actual = sut.update(member.uuid(), request);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
