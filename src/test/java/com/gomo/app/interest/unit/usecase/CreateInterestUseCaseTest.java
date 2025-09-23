package com.gomo.app.interest.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.gomo.app.interest.application.CreateInterestUseCase;
import com.gomo.app.interest.application.port.ReadRegistrantPortOut;
import com.gomo.app.interest.application.port.UploadLogoPortOut;
import com.gomo.app.interest.application.port.command.CreateInterestCommand;
import com.gomo.app.interest.application.port.dto.CreateInterestDto;
import com.gomo.app.interest.application.port.dto.LogoDto;
import com.gomo.app.interest.application.port.dto.RegistrantDto;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestQuota;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.fixture.InterestFixture;
import com.gomo.app.interest.presentation.response.CreateInterestResponse;

@DisplayName("[Application unit]: 관심사 등록 테스트")
@ExtendWith(MockitoExtension.class)
public class CreateInterestUseCaseTest {

	@InjectMocks
	private CreateInterestUseCase sut;

	@Mock
	private ReadRegistrantPortOut readRegistrantPortOut;

	@Mock
	private UploadLogoPortOut uploadLogoPortOut;

	@Mock
	private InterestRepository interestRepository;

	@DisplayName("관심사를 등록한다.")
	@Test
	void create_interest() {
		Interest interest = InterestFixture.create();
		doReturn(RegistrantDto.of(UUID.randomUUID(), "BASIC")).when(readRegistrantPortOut).find(any());
		doReturn(LogoDto.of(interest.getLogo().getUrl())).when(uploadLogoPortOut).upload(any(MockMultipartFile.class));
		doReturn((long)(InterestQuota.BASIC.getMaxCount() - 1)).when(interestRepository).countAllByRegistrantId(any(RegistrantId.class));
		doReturn(interest).when(interestRepository).save(any(Interest.class));

		CreateInterestDto actual = sut.create(
			CreateInterestCommand.of(
				interest.registrantUuid(),
				interest.getName().toString(),
				"#0000FF",
				new MockMultipartFile("logoFile", "mock image data".getBytes()
				)
			)
		);

		assertThat(actual).usingRecursiveComparison().isEqualTo(CreateInterestResponse.of(interest.uuid()));
	}
}
