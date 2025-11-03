package com.gomo.app.core.interest.adapter.out.client;

import static org.mockito.Mockito.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.interest.application.port.dto.RegistrantDto;
import com.gomo.app.core.member.application.port.dto.MemberDto;
import com.gomo.app.core.member.application.port.in.MemberReader;
import com.gomo.app.core.member.domain.model.SubscriptionPlan;
import com.gomo.app.core.member.fixture.MemberFixture;

@DisplayName("[Adapter unit]: 등록자 조회")
@ExtendWith(MockitoExtension.class)
class RegistrantClientTest {

	@InjectMocks
	RegistrantClient sut;

	@Mock
	MemberReader memberReader;

	@DisplayName("관심사 등록자를 조회한다.")
	@Test
	void find_registrant() {
		MemberDto memberDto = MemberDto.from(MemberFixture.create(SubscriptionPlan.PREMIUM), 1000);
		doReturn(memberDto).when(memberReader).read(any());

		RegistrantDto registrantDto = sut.find(memberDto.id());

		Assertions.assertThat(registrantDto.subscriptionPlan()).isEqualTo(SubscriptionPlan.PREMIUM.name());
	}
}
