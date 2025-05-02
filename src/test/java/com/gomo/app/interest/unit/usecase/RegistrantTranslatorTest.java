package com.gomo.app.interest.unit.usecase;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.interest.application.translator.RegistrantTranslator;
import com.gomo.app.interest.domain.model.InterestQuota;
import com.gomo.app.interest.domain.model.Registrant;
import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.SubscriptionPlan;

@DisplayName("[Application unit]: Member -> Registrant 전환 테스트")
@ExtendWith(MockitoExtension.class)
public class RegistrantTranslatorTest {

	@DisplayName("SubscriptionPlan.FREE는 InterestQuota.FREE로 전환된다.")
	@Test
	void free_to_free() {
		Member member = MemberFixture.member(SubscriptionPlan.FREE);
		Registrant registrant = RegistrantTranslator.from(member);

		assertThat(registrant.getInterestQuota()).isEqualTo(InterestQuota.FREE);
	}

	@DisplayName("SubscriptionPlan.BASIC은 InterestQuota.BASIC으로 전환된다.")
	@Test
	void basic_to_basic() {
		Member member = MemberFixture.member(SubscriptionPlan.BASIC);
		Registrant registrant = RegistrantTranslator.from(member);

		assertThat(registrant.getInterestQuota()).isEqualTo(InterestQuota.BASIC);
	}

	@DisplayName("SubscriptionPlan.PREMIUM은 InterestQuota.PREMIUM으로 전환된다.")
	@Test
	void premium_to_premium() {
		Member member = MemberFixture.member(SubscriptionPlan.PREMIUM);
		Registrant registrant = RegistrantTranslator.from(member);

		assertThat(registrant.getInterestQuota()).isEqualTo(InterestQuota.PREMIUM);
	}
}
