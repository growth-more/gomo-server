package com.gomo.app.interest.unit.usecase;

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

import com.gomo.app.common.domain.service.ImageService;
import com.gomo.app.interest.application.UpdateLogoUseCase;
import com.gomo.app.interest.common.fixture.InterestFixture;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.repository.InterestRepository;

@DisplayName("[Application unit]: 관심사 로고 수정 테스트")
@ExtendWith(MockitoExtension.class)
public class UpdateLogoUseCaseTest {

	@InjectMocks
	private UpdateLogoUseCase sut;

	@Mock
	private ImageService imageService;

	@Mock
	private InterestRepository interestRepository;

	@DisplayName("사용자가 등록해둔 관심사 로고를 새로운 로고로 변경한다.")
	@Test
	void update_interest() {
		Interest expected = InterestFixture.interest();
		doReturn(Optional.of(expected)).when(interestRepository).findById(any(InterestId.class));

		sut.update(InterestId.of(UUID.randomUUID()), new MockMultipartFile("logo", "mock image data".getBytes()));

		verify(interestRepository, times(1)).findById(any(InterestId.class));
		verify(imageService, times(1)).uploadImage(any(MockMultipartFile.class));
		verify(imageService, times(1)).deleteImage(any(String.class));
	}

	@DisplayName("기본 관심사 로고를 새로운 로고로 변경한다.")
	@Test
	void update_interest_by_unauthorized_accessor() {
		Interest expected = InterestFixture.defaultLogo();
		doReturn(Optional.of(expected)).when(interestRepository).findById(any(InterestId.class));

		sut.update(InterestId.of(UUID.randomUUID()), new MockMultipartFile("logo", "mock image data".getBytes()));

		verify(interestRepository, times(1)).findById(any(InterestId.class));
		verify(imageService, times(1)).uploadImage(any(MockMultipartFile.class));
	}
}
