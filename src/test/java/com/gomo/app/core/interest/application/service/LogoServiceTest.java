package com.gomo.app.core.interest.application.service;

import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.core.interest.application.port.out.LogoDeleter;
import com.gomo.app.core.interest.application.port.out.LogoUploader;
import com.gomo.app.core.interest.domain.model.Logo;
import com.gomo.app.core.interest.fixture.InterestFixture;

@DisplayName("[Application unit]: 관심사 로고 수정 테스트")
@ExtendWith(MockitoExtension.class)
public class LogoServiceTest {

	@InjectMocks
	private LogoService sut;

	@Mock
	private InterestService interestService;

	@Mock
	private LogoUploader logoUploader;

	@Mock
	private LogoDeleter logoDeleter;

	@DisplayName("사용자가 등록해둔 관심사 로고를 새로운 로고로 변경한다.")
	@Test
	void update_interest() {
		doReturn(InterestFixture.create()).when(interestService).readById(any());
		doReturn(Optional.of("logoUrl")).when(logoUploader).upload(any(MultipartFile.class));

		sut.update(UUID.randomUUID(), new MockMultipartFile("logoFile", "mock image data".getBytes()));

		verify(logoUploader, times(1)).upload(any(MockMultipartFile.class));
		verify(logoDeleter, times(1)).delete(any(String.class));
	}

	@DisplayName("기본 관심사 로고를 새로운 로고로 변경한다.")
	@Test
	void update_interest_by_unauthorized_accessor() {
		doReturn(InterestFixture.create(Logo.of(null))).when(interestService).readById(any());
		doReturn(Optional.of("logoUrl")).when(logoUploader).upload(any(MultipartFile.class));

		sut.update(UUID.randomUUID(), new MockMultipartFile("logoFile", "mock image data".getBytes()));

		verify(logoUploader, times(1)).upload(any(MockMultipartFile.class));
	}
}
