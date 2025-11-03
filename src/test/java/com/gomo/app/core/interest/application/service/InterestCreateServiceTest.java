package com.gomo.app.core.interest.application.service;

import static org.assertj.core.api.Assertions.*;
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

import com.gomo.app.core.interest.application.port.command.CreateInterestCommand;
import com.gomo.app.core.interest.application.port.dto.RegistrantDto;
import com.gomo.app.core.interest.application.port.out.LogoUploader;
import com.gomo.app.core.interest.application.port.out.RegistrantReader;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestQuota;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.core.interest.fixture.InterestFixture;

@DisplayName("[Application unit]: 관심사 등록 테스트")
@ExtendWith(MockitoExtension.class)
public class InterestCreateServiceTest {

	@InjectMocks
	private InterestCreateService sut;

	@Mock
	private RegistrantReader registrantReader;

	@Mock
	private LogoUploader logoUploader;

	@Mock
	private InterestRepository interestRepository;

	@DisplayName("관심사를 등록한다.")
	@Test
	void create_interest() {
		Interest interest = InterestFixture.create();
		doReturn(RegistrantDto.of(UUID.randomUUID(), "BASIC")).when(registrantReader).find(any());
		doReturn(Optional.of(interest.logoUrl())).when(logoUploader).upload(any(MockMultipartFile.class));
		doReturn((long)(InterestQuota.BASIC.getMaxCount() - 1)).when(interestRepository).countAllByRegistrantId(any());
		doReturn(interest).when(interestRepository).save(any(Interest.class));

		UUID actual = sut.create(CreateInterestCommand.of(
			interest.registrantId(), interest.getName().toString(), "#0000FF", new MockMultipartFile("logoFile", "mock image data".getBytes())
		));

		assertThat(actual).isEqualTo(interest.getId());
	}
}
