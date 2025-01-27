package com.gomo.app.interest.common.fixture;

import java.util.UUID;

import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.InterestName;
import com.gomo.app.interest.domain.model.RegistrantId;

public class InterestFixture {

	private static final String INTEREST_ID = "034f680e-dbaf-11ef-aea3-712ffe8f80bc";
	private static final String REGISTRANT_ID = "0c144731-dbaf-11ef-a2e9-e1970ec41c80";
	private static final String INTEREST_NAME = "Interest Name";
	private static final String LOGO_URL = "https://image.nurdykim.me/gomo/interest-logo.png";

	public static Interest interest() {
		return Interest.of(
			InterestId.of(UUID.fromString(INTEREST_ID)),
			RegistrantId.of(UUID.fromString(REGISTRANT_ID)),
			InterestName.of(INTEREST_NAME),
			LOGO_URL
		);
	}
}
