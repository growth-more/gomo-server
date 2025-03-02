package com.gomo.app.member.unit.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.gomo.app.member.domain.service.MemberValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Optional;

import com.gomo.app.common.domain.service.ImageService;
import com.gomo.app.member.application.UpdateMemberUseCase;
import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.presentation.request.UpdateHandleRequest;
import com.gomo.app.member.presentation.request.UpdateMemberRequest;
import com.gomo.app.member.presentation.request.UpdatePasswordRequest;
import com.gomo.app.member.presentation.request.UpdateProfileImageRequest;
import com.gomo.app.member.presentation.response.UpdateProfileImageResponse;

@DisplayName("[Application Unit]: 멤버 수정 기능 테스트")
@ExtendWith(MockitoExtension.class)
public class UpdateMemberUseCaseTest {
    @InjectMocks
    private UpdateMemberUseCase sut;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordService passwordService;

    @Mock
    private ImageService imageService;

    @Mock
    private MemberValidator memberValidator;

    private static final String NEW_NAME = "NewName";
    private static final String NEW_MOTTO = "NewMotto";
    private static final String NEW_HANDLE = "@update_handle";
    private static final String NEW_PASSWORD = "Update123!";
    private static final String ENCRYPT_PASSWORD = "Encrypt123!";
    private static final String PASSWORD = "Test123!";
    private static final String NEW_IMAGE_URL = "https://example.com/profile.jpg";

    @DisplayName("회원 정보(모토, 이름)를 수정한다.")
    @Test
    void update_member_name_and_motto(){
        Member member = MemberFixture.member(passwordService);
        UpdateMemberRequest request = UpdateMemberRequest.of(NEW_NAME, NEW_MOTTO);

        doReturn(Optional.of(member)).when(memberRepository).findById(member.getId());

        sut.update(member.getId(), request);

        assertThat(member.getName().getName()).isEqualTo(NEW_NAME);
        assertThat(member.getMotto().getMotto()).isEqualTo(NEW_MOTTO);
    }

    @DisplayName("프로필 이미지를 업데이트 한다.")
    @Test
    void update_member_profile(){
        Member member = MemberFixture.member(passwordService);
        MockMultipartFile request = new MockMultipartFile("profile", "mock image data".getBytes());
        UpdateProfileImageResponse expected = UpdateProfileImageResponse.of(NEW_IMAGE_URL);

        doReturn(Optional.of(member)).when(memberRepository).findById(member.getId());
        doReturn(NEW_IMAGE_URL).when(imageService).uploadImage(any());

        UpdateProfileImageResponse actual = sut.updateProfileImage(member.getId(), request);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("비밀번호를 업데이트 한다.")
    @Test
    void update_member_password(){
        Member member = MemberFixture.member(passwordService);
        UpdatePasswordRequest request = UpdatePasswordRequest.of(PASSWORD, NEW_PASSWORD);

        doReturn(Optional.of(member)).when(memberRepository).findById(member.getId());
        doReturn(ENCRYPT_PASSWORD).when(passwordService).encode(NEW_PASSWORD);
        doReturn(true).when(passwordService).matches(PASSWORD, member.getPassword().getPassword());

        sut.updatePassword(member.getId(), request);

        assertThat(member.getPassword().getPassword()).isEqualTo(ENCRYPT_PASSWORD);
    }

    @DisplayName("멤버 핸들을 업데이트 한다.")
    @Test
    void update_member_handle(){
        Member member = MemberFixture.member(passwordService);
        UpdateHandleRequest request = UpdateHandleRequest.of(NEW_HANDLE);
        doReturn(Optional.of(member)).when(memberRepository).findById(member.getId());
        doNothing().when(memberValidator).checkDuplicatedHandle(request.getHandle());

        sut.updateHandle(member.getId(), request);
        assertThat(member.getHandle().getHandle()).isEqualTo(NEW_HANDLE);
    }
}
