package com.gomo.app.interest.unit.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.interest.domain.model.InterestQuota;
import com.gomo.app.interest.exception.InterestConstraintViolationException;

@DisplayName("[Domain unit]: 관심사 할당량 검증 기능 테스트")
public class InterestQuotaTest {

	@DisplayName("FREE 플랜 할당량을 초과하지 않는다.")
	@Test
	void not_exceed_free_plan_quota() {
		InterestQuota quota = InterestQuota.FREE;

		assertThatCode(() -> quota.validateCount(InterestQuota.FREE.getMaxCount() - 1))
			.doesNotThrowAnyException();
	}

	@DisplayName("FREE 플랜 할당량을 초과한다.")
	@Test
	void exceed_free_plan_quota() {
		InterestQuota quota = InterestQuota.FREE;

		assertThatThrownBy(() -> quota.validateCount(InterestQuota.FREE.getMaxCount()))
			.isInstanceOf(InterestConstraintViolationException.class);
	}

	@DisplayName("BASIC 플랜 할당량을 초과하지 않는다.")
	@Test
	void not_exceed_basic_plan_quota() {
		InterestQuota quota = InterestQuota.BASIC;

		assertThatCode(() -> quota.validateCount(InterestQuota.BASIC.getMaxCount() - 1))
			.doesNotThrowAnyException();
	}

	@DisplayName("BASIC 플랜 할당량을 초과한다.")
	@Test
	void exceed_basic_plan_quota() {
		InterestQuota quota = InterestQuota.BASIC;

		assertThatThrownBy(() -> quota.validateCount(InterestQuota.BASIC.getMaxCount()))
			.isInstanceOf(InterestConstraintViolationException.class);
	}

	@DisplayName("PREMIUM 플랜 할당량을 초과하지 않는다.")
	@Test
	void not_exceed_premium_plan_quota() {
		InterestQuota quota = InterestQuota.PREMIUM;

		assertThatCode(() -> quota.validateCount(InterestQuota.PREMIUM.getMaxCount() - 1))
			.doesNotThrowAnyException();
	}

	@DisplayName("PREMIUM 플랜 할당량을 초과한다.")
	@Test
	void exceed_premium_plan_quota() {
		InterestQuota quota = InterestQuota.PREMIUM;

		assertThatThrownBy(() -> quota.validateCount(InterestQuota.PREMIUM.getMaxCount()))
			.isInstanceOf(InterestConstraintViolationException.class);
	}
}
