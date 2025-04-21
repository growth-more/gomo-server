package com.gomo.app.interest.application.translator;

import com.gomo.app.interest.domain.model.InterestQuota;
import com.gomo.app.member.domain.model.SubscriptionPlan;

public class InterestQuotaTranslator {

	public static InterestQuota from(SubscriptionPlan plan) {
		return switch (plan) {
			case FREE -> InterestQuota.FREE;
			case BASIC -> InterestQuota.BASIC;
			case PREMIUM -> InterestQuota.PREMIUM;
		};
	}
}
