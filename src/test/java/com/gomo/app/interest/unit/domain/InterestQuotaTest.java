package com.gomo.app.interest.unit.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.interest.domain.model.InterestQuota;

@DisplayName("[Domain unit]: 관심사 할당량 검증 기능 테스트")
public class InterestQuotaTest {

	@DisplayName("FREE 플랜 할당량을 초과하지 않는다.")
	@Test
	void not_exceed_free_plan_quota() {
		InterestQuota quota = InterestQuota.FREE;

		boolean actual = quota.isExceed(InterestQuota.FREE.getMaxCount() - 1);

		assertThat(actual).isFalse();
	}

	@DisplayName("FREE 플랜 할당량을 초과한다.")
	@Test
	void exceed_free_plan_quota() {
		InterestQuota quota = InterestQuota.FREE;

		boolean actual = quota.isExceed(InterestQuota.FREE.getMaxCount());

		assertThat(actual).isTrue();
	}

	@DisplayName("BASIC 플랜 할당량을 초과하지 않는다.")
	@Test
	void not_exceed_basic_plan_quota() {
		InterestQuota quota = InterestQuota.BASIC;

		boolean actual = quota.isExceed(InterestQuota.BASIC.getMaxCount() - 1);

		assertThat(actual).isFalse();
	}

	@DisplayName("BASIC 플랜 할당량을 초과한다.")
	@Test
	void exceed_basic_plan_quota() {
		InterestQuota quota = InterestQuota.BASIC;

		boolean actual = quota.isExceed(InterestQuota.BASIC.getMaxCount());

		assertThat(actual).isTrue();
	}

	@DisplayName("PREMIUM 플랜 할당량을 초과하지 않는다.")
	@Test
	void not_exceed_premium_plan_quota() {
		InterestQuota quota = InterestQuota.PREMIUM;

		boolean actual = quota.isExceed(InterestQuota.PREMIUM.getMaxCount() - 1);

		assertThat(actual).isFalse();
	}

	@DisplayName("PREMIUM 플랜 할당량을 초과한다.")
	@Test
	void exceed_premium_plan_quota() {
		InterestQuota quota = InterestQuota.PREMIUM;

		boolean actual = quota.isExceed(InterestQuota.PREMIUM.getMaxCount());

		assertThat(actual).isTrue();
	}
}
