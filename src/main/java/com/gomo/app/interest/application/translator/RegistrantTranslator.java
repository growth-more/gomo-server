package com.gomo.app.interest.application.translator;

import com.gomo.app.interest.domain.model.InterestQuota;
import com.gomo.app.interest.domain.model.Registrant;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.SubscriptionPlan;

public class RegistrantTranslator {

	public static Registrant from(Member member) {
		return Registrant.of(
			RegistrantId.of(member.uuid()),
			transferFrom(member.getSubscriptionPlan())
		);
	}

	private static InterestQuota transferFrom(SubscriptionPlan subscriptionPlan) {
		return switch (subscriptionPlan) {
			case FREE -> InterestQuota.FREE;
			case BASIC -> InterestQuota.BASIC;
			case PREMIUM -> InterestQuota.PREMIUM;
		};
	}
}
