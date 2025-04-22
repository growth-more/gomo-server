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

import com.gomo.app.common.domain.service.ImageService;
import com.gomo.app.interest.application.CreateInterestUseCase;
import com.gomo.app.interest.common.fixture.InterestFixture;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestQuota;
import com.gomo.app.interest.domain.service.InterestService;
import com.gomo.app.interest.presentation.request.CreateInterestRequest;
import com.gomo.app.interest.presentation.response.CreateInterestResponse;
import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.model.SubscriptionPlan;
import com.gomo.app.member.domain.service.ReadMemberService;

@DisplayName("[Application unit]: 관심사 등록 테스트")
@ExtendWith(MockitoExtension.class)
public class CreateInterestUseCaseTest {

	@InjectMocks
	private CreateInterestUseCase sut;

	@Mock
	private ReadMemberService readMemberService;

	@Mock
	private ImageService imageService;

	@Mock
	private InterestService interestService;

	@DisplayName("관심사를 등록한다.")
	@Test
	void create_interest() {
		Interest interest = InterestFixture.interest();
		doReturn(MemberFixture.member(SubscriptionPlan.BASIC)).when(readMemberService).find(any(UUID.class));
		doReturn(interest.getLogo().getUrl()).when(imageService).uploadImage(any(MockMultipartFile.class));
		doReturn(interest).when(interestService).create(any(Interest.class), any(InterestQuota.class));

		CreateInterestResponse actual = sut.create(
			interest.getRegistrantId(),
			CreateInterestRequest.of(interest.getName().toString(), "#0000FF", new MockMultipartFile("logo", "mock image data".getBytes()))
		);

		assertThat(actual).usingRecursiveComparison().isEqualTo(CreateInterestResponse.of(interest.getId()));
	}
}
