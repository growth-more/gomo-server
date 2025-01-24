package com.gomo.app.interest.common.fixture.interest;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.InterestName;
import com.gomo.app.interest.domain.model.Level;
import com.gomo.app.interest.domain.model.Proficiency;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.model.Score;

/**
 * 테스트 샘플 관심사
 * 실제 데이터베이스에 존재하는 테스트 레코드와 동일한 값을 수동으로 지정해서 사용한다.
 * @ 해당 관심사는 주요 관심사로 등록되어 있지 않다.
 * @ 새로운 주요 관심사 등록 테스트에서 사용한다.
 */
@Component
public class BackendInterestFixture {

	private static final String ID = "3bd1b3f7-d7c6-11ef-abb8-a7e09b2a499c";
	private static final String REGISTRANT_ID = "a10581ce-d721-11ef-a8a5-2508e2a6438b";
	private static final String NAME = "Backend";
	private static final String LOGO_URL = "https://mini-cloud/backend-logo.png";
	private static final int LEVEL = 10;
	private static final int SCORE = 450;
	private static final int TOTAL_SCORE = 4450;

	public static Interest backend() {
		return new Interest(
			InterestId.of(UUID.fromString(ID)),
			RegistrantId.of(UUID.fromString(REGISTRANT_ID)),
			new Proficiency(new Level(LEVEL), new Score(SCORE), TOTAL_SCORE),
			InterestName.of(NAME),
			LOGO_URL
		);
	}

	public static String id() {
		return ID;
	}

	public static String name() {
		return NAME;
	}

	public static String logoUrl() {
		return LOGO_URL;
	}

	public static int level() {
		return LEVEL;
	}

	public static int score() {
		return SCORE;
	}

	public static int totalScore() {
		return TOTAL_SCORE;
	}
}
