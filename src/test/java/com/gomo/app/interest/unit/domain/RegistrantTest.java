package com.gomo.app.interest.unit.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.interest.domain.model.InterestQuota;
import com.gomo.app.interest.domain.model.Registrant;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.exception.InterestConstraintViolationException;
import com.gomo.app.interest.exception.code.InterestErrorCode;

@DisplayName("[Domain unit]: 관심사 등록자 엔티티 테스트")
public class RegistrantTest {

	@DisplayName("등록자의 관심사 수가 할당량을 초과하지 않는다.")
	@Test
	void not_exceed_interest_quota() {
		Registrant registrant = Registrant.of(RegistrantId.of(UUID.randomUUID()), InterestQuota.FREE.name());

		assertThatCode(() -> registrant.validateInterestQuota(InterestQuota.FREE.getMaxCount() - 1))
			.doesNotThrowAnyException();
	}

	@DisplayName("등록자의 관심사 수가 할당량을 초과한다.")
	@Test
	void exceed_interest_quota() {
		Registrant registrant = Registrant.of(RegistrantId.of(UUID.randomUUID()), InterestQuota.FREE.name());

		assertThatThrownBy(() -> registrant.validateInterestQuota(InterestQuota.FREE.getMaxCount()))
			.isInstanceOf(InterestConstraintViolationException.class)
			.hasMessageContaining(InterestErrorCode.EXCEED_QUOTA.getMessage());
	}

	@DisplayName("SubscriptionPlan.FREE는 InterestQuota.FREE로 전환된다.")
	@Test
	void free_to_free() {
		Registrant registrant = Registrant.of(RegistrantId.of(UUID.randomUUID()), "FREE");
		assertThat(registrant.getInterestQuota()).isEqualTo(InterestQuota.FREE);
	}

	@DisplayName("SubscriptionPlan.BASIC은 InterestQuota.BASIC으로 전환된다.")
	@Test
	void basic_to_basic() {
		Registrant registrant = Registrant.of(RegistrantId.of(UUID.randomUUID()), "BASIC");
		assertThat(registrant.getInterestQuota()).isEqualTo(InterestQuota.BASIC);
	}

	@DisplayName("SubscriptionPlan.PREMIUM은 InterestQuota.PREMIUM으로 전환된다.")
	@Test
	void premium_to_premium() {
		Registrant registrant = Registrant.of(RegistrantId.of(UUID.randomUUID()), "PREMIUM");
		assertThat(registrant.getInterestQuota()).isEqualTo(InterestQuota.PREMIUM);
	}
}
