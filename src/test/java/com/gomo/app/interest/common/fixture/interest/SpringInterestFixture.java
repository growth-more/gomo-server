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
 * 관심사 픽스처
 * 실제 데이터베이스에 존재하는 테스트 레코드와 동일한 값을 수동으로 지정해서 사용한다.
 * @ 해당 관심사는 이미 주요 관심사로 등록되어 있다.
 * @ 주요 관심사 중복 생성 테스트에서 사용한다.
 */
@Component
public class SpringInterestFixture {

	private static final String ID = "90a387a7-d7c5-11ef-b4d7-079c7dc41274";
	private static final String REGISTRANT_ID = "a10581ce-d721-11ef-a8a5-2508e2a6438b";
	private static final int LEVEL = 5;
	private static final int SCORE = 20;
	private static final int TOTAL_SCORE = 2020;
	private static final String NAME = "Spring";
	private static final String LOGO_URL = "https://mini-cloud/spring-logo.png";

	public static Interest spring() {
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
