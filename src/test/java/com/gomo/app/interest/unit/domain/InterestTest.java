package com.gomo.app.interest.unit.domain;

import static com.gomo.app.interest.common.constant.InterestFieldName.*;
import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.InterestName;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.exception.InterestAccessDeniedException;

@DisplayName("[Domain unit]: 관심사 생성 및 수정 테스트")
public class InterestTest {

	private static final InterestId ID_PARAM = InterestId.of(UUID.randomUUID());
	private static final RegistrantId REGISTRANT_ID_PARAM = RegistrantId.of(UUID.randomUUID());
	private static final RegistrantId NOT_REGISTRANT_ID_PARAM = RegistrantId.of(UUID.randomUUID());
	private static final InterestName NAME_PARAM = InterestName.of("interest name");
	private static final InterestName UPDATED_NAME_PARAM = InterestName.of("updated interest name");
	private static final String LOGO_URL_PARAM = "https://image.nurdykim.me/gomo/logo-param.png";
	private static final String DEFAULT_LOGO_URL = "https://image.nurdykim.me/gomo/default-logo.png";
	private static final String UPDATED_LOGO_URL_PARAM = "https://mini-cloud/updated_logo-param.png";

	@DisplayName("관심사를 생성한다.")
	@Test
	void create_interest() {
		Interest interest = Interest.of(ID_PARAM, REGISTRANT_ID_PARAM, NAME_PARAM, LOGO_URL_PARAM);

		assertThat(interest)
			.extracting(ID, REGISTRANT_ID, NAME, LOGO_URL)
			.containsExactly(ID_PARAM, REGISTRANT_ID_PARAM, NAME_PARAM, LOGO_URL_PARAM);
	}

	@DisplayName("관심사 로고를 등록하지 않으면 기본 이미지가 등록된다.")
	@Test
	void create_interest_with_default_logo() {
		Interest interest = Interest.of(ID_PARAM, REGISTRANT_ID_PARAM, NAME_PARAM, null);

		assertThat(interest)
			.extracting(ID, REGISTRANT_ID, NAME, LOGO_URL)
			.containsExactly(ID_PARAM, REGISTRANT_ID_PARAM, NAME_PARAM, DEFAULT_LOGO_URL);
	}

	@DisplayName("관심사 이름을 수정한다.")
	@Test
	void update_interest_name() {
		Interest interest = Interest.of(ID_PARAM, REGISTRANT_ID_PARAM, NAME_PARAM, LOGO_URL_PARAM);
		interest.updateName(UPDATED_NAME_PARAM);

		assertThat(interest)
			.extracting(ID, REGISTRANT_ID, NAME, LOGO_URL)
			.containsExactly(ID_PARAM, REGISTRANT_ID_PARAM, UPDATED_NAME_PARAM, LOGO_URL_PARAM);
	}

	@DisplayName("관심사 이름을 수정한다.")
	@Test
	void update_interest_logo() {
		Interest interest = Interest.of(ID_PARAM, REGISTRANT_ID_PARAM, NAME_PARAM, LOGO_URL_PARAM);
		interest.updateLogoUrl(UPDATED_LOGO_URL_PARAM);

		assertThat(interest)
			.extracting(ID, REGISTRANT_ID, NAME, LOGO_URL)
			.containsExactly(ID_PARAM, REGISTRANT_ID_PARAM, NAME_PARAM, UPDATED_LOGO_URL_PARAM);
	}

	@DisplayName("관심사는 등록한 사람만 접근할 수 있다.")
	@Test
	void access_interest() {
		Interest interest = Interest.of(ID_PARAM, REGISTRANT_ID_PARAM, NAME_PARAM, LOGO_URL_PARAM);
		interest.validateAuthority(REGISTRANT_ID_PARAM.getId());
	}

	@DisplayName("관심사를 등록한 사람이 아니면 접근할 수 없다.")
	@Test
	void access_denied_interest() {
		Interest interest = Interest.of(ID_PARAM, REGISTRANT_ID_PARAM, NAME_PARAM, LOGO_URL_PARAM);

		assertThatThrownBy(() -> interest.validateAuthority(NOT_REGISTRANT_ID_PARAM.getId()))
			.isInstanceOf(InterestAccessDeniedException.class)
			.hasMessageContaining("Access denied for the interest");
	}

	@DisplayName("관심사 숙련도를 향상한다.")
	@Test
	void enhance_interest_proficiency() {
		Interest interest = Interest.of(ID_PARAM, REGISTRANT_ID_PARAM, NAME_PARAM, LOGO_URL_PARAM);
		interest.enhanceProficiency(10, 400);

		assertThat(interest.getProficiency().getScore().getScore()).isEqualTo(10);
	}
}
