package com.gomo.app.member.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.gomo.app.common.util.JwtUtil;
import com.gomo.app.member.application.LoginMemberUseCase;
import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.model.ActivateStatus;
import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.exception.MemberPolicyViolationException;
import com.gomo.app.member.infrastructure.JwtSessionRedisService;
import com.gomo.app.member.presentation.request.LoginMemberRequest;
import com.gomo.app.member.presentation.response.LoginMemberResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@DisplayName("[Application Unit]: 이메일 로그인 테스트")
@ExtendWith(MockitoExtension.class)
public class LoginMemberUseCaseTest {
    @InjectMocks
    private LoginMemberUseCase sut;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private JwtSessionRedisService jwtSessionRedisService;

    @Mock
    private PasswordService passwordService;

    private static final String EMAIL = "test@naver.com";
    private static final String PASSWORD = "Test123!";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final long EXP_TIME = 2592000000L;

    @DisplayName("이메일 로그인에 성공한다.")
    @Test
    void login_successfully(){
        Member member = MemberFixture.member(passwordService);
        LoginMemberRequest request = LoginMemberRequest.of(EMAIL, PASSWORD);
        LoginMemberResponse expected = LoginMemberResponse.of(member.getId(), ACCESS_TOKEN, REFRESH_TOKEN,EXP_TIME);

        doReturn(Optional.of(member)).when(memberRepository).findByEmail(any(Email.class));
        doReturn(true).when(passwordService).matches(PASSWORD, member.getPassword().getPassword());
        doReturn(ACCESS_TOKEN).when(jwtUtil).generateAccessToken(member.getId());
        doReturn(REFRESH_TOKEN).when(jwtUtil).generateRefreshToken(member.getId());
        doReturn(EXP_TIME).when(jwtUtil).extractExpirationTime(REFRESH_TOKEN);

        doNothing().when(jwtSessionRedisService).setRefreshToken(any(), any());

        LoginMemberResponse actual = sut.login(request);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("탈퇴한 이메일 계정으로 로그인 시, 로그인에 실패한다.")
    @Test
    void email_login_with_deleted_email(){
        Member member = MemberFixture.member(ActivateStatus.DELETED, passwordService);
        LoginMemberRequest request = LoginMemberRequest.of(EMAIL, PASSWORD);

        doReturn(Optional.of(member)).when(memberRepository).findByEmail(any(Email.class));

        assertThatThrownBy(() -> sut.login(request))
                .isInstanceOf(MemberPolicyViolationException.class)
                .hasMessageContaining("member info has been deleted. check email or password");
    }

    @DisplayName("정지된 이메일 계정으로 로그인 시, 로그인에 실패한다.")
    @Test
    void email_login_with_blocked_email(){
        Member member = MemberFixture.member(ActivateStatus.BLOCKED,passwordService);
        LoginMemberRequest request = LoginMemberRequest.of(EMAIL, PASSWORD);

        doReturn(Optional.of(member)).when(memberRepository).findByEmail(any(Email.class));

        assertThatThrownBy(() -> sut.login(request))
                .isInstanceOf(MemberPolicyViolationException.class)
                .hasMessageContaining("member info has been blocked. check email or password");
    }
}
