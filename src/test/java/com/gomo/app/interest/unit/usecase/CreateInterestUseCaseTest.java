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

import com.gomo.app.image.ImageService;
import com.gomo.app.interest.application.CreateInterestUseCase;
import com.gomo.app.interest.common.fixture.InterestFixture;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestQuota;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.presentation.request.CreateInterestRequest;
import com.gomo.app.interest.presentation.response.CreateInterestResponse;
import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.model.SubscriptionPlan;
import com.gomo.app.member.domain.service.MemberService;

@DisplayName("[Application unit]: 관심사 등록 테스트")
@ExtendWith(MockitoExtension.class)
public class CreateInterestUseCaseTest {

	@InjectMocks
	private CreateInterestUseCase sut;

	@Mock
	private MemberService memberService;

	@Mock
	private ImageService imageService;

	@Mock
	private InterestRepository interestRepository;

	@DisplayName("관심사를 등록한다.")
	@Test
	void create_interest() {
		Interest interest = InterestFixture.interest();
		doReturn(MemberFixture.member(SubscriptionPlan.BASIC)).when(memberService).find(any(MemberId.class));
		doReturn(interest.getLogo().getUrl()).when(imageService).uploadImage(any(MockMultipartFile.class));
		doReturn((long)(InterestQuota.BASIC.getMaxCount() - 1)).when(interestRepository).countAllByRegistrantId(any(RegistrantId.class));
		doReturn(interest).when(interestRepository).save(any(Interest.class));

		CreateInterestResponse actual = sut.create(
			interest.registrantUuid(),
			CreateInterestRequest.of(interest.getName().toString(), "#0000FF", new MockMultipartFile("logo", "mock image data".getBytes()))
		);

		assertThat(actual).usingRecursiveComparison().isEqualTo(CreateInterestResponse.of(interest.getId()));
	}
}
