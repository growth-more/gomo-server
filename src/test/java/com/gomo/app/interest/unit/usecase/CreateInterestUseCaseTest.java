package com.gomo.app.interest.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.gomo.app.common.domain.service.ImageService;
import com.gomo.app.interest.application.CreateInterestUseCase;
import com.gomo.app.interest.common.fixture.InterestFixture;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.presentation.request.CreateInterestRequest;
import com.gomo.app.interest.presentation.response.CreateInterestResponse;

@DisplayName("[Application unit]: 관심사 등록 테스트")
@ExtendWith(MockitoExtension.class)
public class CreateInterestUseCaseTest {

	@InjectMocks
	private CreateInterestUseCase sut;

	@Mock
	private ImageService imageService;

	@Mock
	private InterestRepository interestRepository;

	@DisplayName("관심사를 등록한다.")
	@Test
	void create_interest() {
		Interest interest = InterestFixture.interest();
		CreateInterestResponse expected = CreateInterestResponse.of(interest.getId());
		doReturn(interest.getLogoUrl()).when(imageService).uploadImage(any(MockMultipartFile.class));
		doReturn(interest).when(interestRepository).save(any(Interest.class));

		CreateInterestResponse actual = sut.create(
			interest.getRegistrantId(),
			CreateInterestRequest.of(interest.getName().toString(), "000000"),
			new MockMultipartFile("logo", "mock image data".getBytes())
		);

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}
}
