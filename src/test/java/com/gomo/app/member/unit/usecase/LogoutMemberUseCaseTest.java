package com.gomo.app.member.unit.usecase;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.member.application.LogoutMemberUseCase;
import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.infrastructure.JwtSessionRedisService;

@DisplayName("[Application Unit]: 로그아웃 테스트")
@ExtendWith(MockitoExtension.class)
public class LogoutMemberUseCaseTest {

    @InjectMocks
    private LogoutMemberUseCase sut;

    @Mock
    private JwtSessionRedisService jwtSessionRedisService;

    @DisplayName("로그아웃 테스트")
    @Test
    public void logout_member_successful(){
        Member member = MemberFixture.member();

        doNothing().when(jwtSessionRedisService).deleteRefreshToken(member.getId().getId());
        sut.logout(member.getId().getId());

        verify(jwtSessionRedisService, times(1)).deleteRefreshToken(member.getId().getId());
    }
}
