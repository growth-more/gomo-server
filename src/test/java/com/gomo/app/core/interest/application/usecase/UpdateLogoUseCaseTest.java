package com.gomo.app.core.interest.application.usecase;

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

import com.gomo.app.core.interest.domain.model.InterestId;
import com.gomo.app.core.interest.domain.model.Logo;
import com.gomo.app.core.interest.domain.service.InterestService;
import com.gomo.app.core.interest.fixture.InterestFixture;
import com.gomo.app.support.image.application.port.DeleteImagePortIn;
import com.gomo.app.support.image.application.port.UploadImagePortIn;

@DisplayName("[Application unit]: 관심사 로고 수정 테스트")
@ExtendWith(MockitoExtension.class)
public class UpdateLogoUseCaseTest {

	@InjectMocks
	private UpdateLogoUseCase sut;

	@Mock
	private InterestService interestService;

	@Mock
	private UploadImagePortIn uploadImagePortIn;

	@Mock
	private DeleteImagePortIn deleteImagePortIn;

	@DisplayName("사용자가 등록해둔 관심사 로고를 새로운 로고로 변경한다.")
	@Test
	void update_interest() {
		doReturn(InterestFixture.create()).when(interestService).find(any(InterestId.class));
		doReturn(Optional.of("logoUrl")).when(uploadImagePortIn).upload(any(MultipartFile.class));

		sut.update(InterestId.of(UUID.randomUUID()), new MockMultipartFile("logoFile", "mock image data".getBytes()));

		verify(interestService, times(1)).find(any(InterestId.class));
		verify(uploadImagePortIn, times(1)).upload(any(MockMultipartFile.class));
		verify(deleteImagePortIn, times(1)).delete(any(String.class));
	}

	@DisplayName("기본 관심사 로고를 새로운 로고로 변경한다.")
	@Test
	void update_interest_by_unauthorized_accessor() {
		doReturn(InterestFixture.create(Logo.of(null))).when(interestService).find(any(InterestId.class));
		doReturn(Optional.of("logoUrl")).when(uploadImagePortIn).upload(any(MultipartFile.class));

		sut.update(InterestId.of(UUID.randomUUID()), new MockMultipartFile("logoFile", "mock image data".getBytes()));

		verify(interestService, times(1)).find(any(InterestId.class));
		verify(uploadImagePortIn, times(1)).upload(any(MockMultipartFile.class));
	}
}
