package com.gomo.app.interest.unit.usecase;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.interest.application.translator.InterestQuotaTranslator;
import com.gomo.app.interest.domain.model.InterestQuota;
import com.gomo.app.member.domain.model.SubscriptionPlan;

@DisplayName("[Application unit]: SubscriptionPlan -> InterestQuota 전환 테스트")
@ExtendWith(MockitoExtension.class)
public class InterestQuotaTranslatorTest {

	@DisplayName("SubscriptionPlan.FREE는 InterestQuota.FREE로 전환된다.")
	@Test
	void free_to_free() {
		InterestQuota interestQuota = InterestQuotaTranslator.from(SubscriptionPlan.FREE);

		assertThat(interestQuota).isEqualTo(InterestQuota.FREE);
	}

	@DisplayName("SubscriptionPlan.BASIC은 InterestQuota.BASIC으로 전환된다.")
	@Test
	void basic_to_basic() {
		InterestQuota interestQuota = InterestQuotaTranslator.from(SubscriptionPlan.BASIC);

		assertThat(interestQuota).isEqualTo(InterestQuota.BASIC);
	}

	@DisplayName("SubscriptionPlan.PREMIUM은 InterestQuota.PREMIUM으로 전환된다.")
	@Test
	void premium_to_premium() {
		InterestQuota interestQuota = InterestQuotaTranslator.from(SubscriptionPlan.PREMIUM);

		assertThat(interestQuota).isEqualTo(InterestQuota.PREMIUM);
	}
}
