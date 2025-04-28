package com.gomo.app.interest.unit.usecase;

import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.gomo.app.image.ImageService;
import com.gomo.app.interest.application.UpdateLogoUseCase;
import com.gomo.app.interest.common.fixture.InterestFixture;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.service.InterestService;

@DisplayName("[Application unit]: 관심사 로고 수정 테스트")
@ExtendWith(MockitoExtension.class)
public class UpdateLogoUseCaseTest {

	@InjectMocks
	private UpdateLogoUseCase sut;

	@Mock
	private InterestService interestService;

	@Mock
	private ImageService imageService;

	@DisplayName("사용자가 등록해둔 관심사 로고를 새로운 로고로 변경한다.")
	@Test
	void update_interest() {
		doReturn(InterestFixture.interest()).when(interestService).find(any(InterestId.class));

		sut.update(InterestId.of(UUID.randomUUID()), new MockMultipartFile("logo", "mock image data".getBytes()));

		verify(interestService, times(1)).find(any(InterestId.class));
		verify(imageService, times(1)).uploadImage(any(MockMultipartFile.class));
		verify(imageService, times(1)).deleteImage(any(String.class));
	}

	@DisplayName("기본 관심사 로고를 새로운 로고로 변경한다.")
	@Test
	void update_interest_by_unauthorized_accessor() {
		doReturn(InterestFixture.defaultLogo()).when(interestService).find(any(InterestId.class));

		sut.update(InterestId.of(UUID.randomUUID()), new MockMultipartFile("logo", "mock image data".getBytes()));

		verify(interestService, times(1)).find(any(InterestId.class));
		verify(imageService, times(1)).uploadImage(any(MockMultipartFile.class));
	}
}
