package com.gomo.app.member.unit.usecase;

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

import com.gomo.app.common.domain.service.ImageService;
import com.gomo.app.member.application.UpdateProfileBannerUseCase;
import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.presentation.response.UpdateProfileBannerResponse;

@DisplayName("[Application Unit]: 프로필 배경 수정 기능 테스트")
@ExtendWith(MockitoExtension.class)
public class UpdateProfileBannerUseCaseTest {
    @InjectMocks
    private UpdateProfileBannerUseCase sut;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordService passwordService;

    @Mock
    private ImageService imageService;

    private static final String NEW_IMAGE_URL = "https://example.com/profile.jpg";

    @DisplayName("프로필 배너 업데이트 한다.")
    @Test
    void update_member_banner(){
        Member member = MemberFixture.member(passwordService);
        MockMultipartFile request = new MockMultipartFile("banner", "mock image data".getBytes());
        UpdateProfileBannerResponse expected = UpdateProfileBannerResponse.of(NEW_IMAGE_URL);

        doReturn(Optional.of(member)).when(memberRepository).findById(member.getId());
        doReturn(NEW_IMAGE_URL).when(imageService).uploadImage(any());

        UpdateProfileBannerResponse actual = sut.update(member.getId(), request);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
