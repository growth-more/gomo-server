package com.gomo.app.core.member.adapter.out.client;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.support.auth.application.port.in.RefreshTokenDeleter;

@DisplayName("[Adapter Unit]: 리프레시 토큰 삭제 요청 테스트")
@ExtendWith(MockitoExtension.class)
class AuthClientTest {

	@InjectMocks
	private AuthClient sut;

	@Mock
	private RefreshTokenDeleter refreshTokenDeleter;

	@DisplayName("리프레시 토큰 삭제를 요청한다.")
	@Test
	void invalidate_refresh_token() {
		sut.logout(UUID.randomUUID());

		verify(refreshTokenDeleter, times(1)).delete(any());
	}
}
