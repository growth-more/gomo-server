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

import com.gomo.app.core.interest.application.port.out.DeleteLogoPort;
import com.gomo.app.core.interest.application.port.out.UploadLogoPort;
import com.gomo.app.core.interest.domain.model.Logo;
import com.gomo.app.core.interest.domain.service.InterestService;
import com.gomo.app.core.interest.fixture.InterestFixture;

@DisplayName("[Application unit]: 관심사 로고 수정 테스트")
@ExtendWith(MockitoExtension.class)
public class UpdateLogoServiceTest {

	@InjectMocks
	private UpdateLogoService sut;

	@Mock
	private InterestService interestService;

	@Mock
	private UploadLogoPort uploadLogoPort;

	@Mock
	private DeleteLogoPort deleteLogoPort;

	@DisplayName("사용자가 등록해둔 관심사 로고를 새로운 로고로 변경한다.")
	@Test
	void update_interest() {
		doReturn(InterestFixture.create()).when(interestService).find(any());
		doReturn(Optional.of("logoUrl")).when(uploadLogoPort).upload(any(MultipartFile.class));

		sut.update(UUID.randomUUID(), new MockMultipartFile("logoFile", "mock image data".getBytes()));

		verify(interestService, times(1)).find(any());
		verify(uploadLogoPort, times(1)).upload(any(MockMultipartFile.class));
		verify(deleteLogoPort, times(1)).delete(any(String.class));
	}

	@DisplayName("기본 관심사 로고를 새로운 로고로 변경한다.")
	@Test
	void update_interest_by_unauthorized_accessor() {
		doReturn(InterestFixture.create(Logo.of(null))).when(interestService).find(any());
		doReturn(Optional.of("logoUrl")).when(uploadLogoPort).upload(any(MultipartFile.class));

		sut.update(UUID.randomUUID(), new MockMultipartFile("logoFile", "mock image data".getBytes()));

		verify(interestService, times(1)).find(any());
		verify(uploadLogoPort, times(1)).upload(any(MockMultipartFile.class));
	}
}
