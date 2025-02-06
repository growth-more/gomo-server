package com.gomo.app.interest.unit.domain;

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

	private static final InterestId ID = InterestId.of(UUID.randomUUID());
	private static final RegistrantId REGISTRANT_ID = RegistrantId.of(UUID.randomUUID());
	private static final RegistrantId UNAUTHORIZED_ID = RegistrantId.of(UUID.randomUUID());
	private static final InterestName NAME = InterestName.of("interest name");
	private static final String LOGO_URL = "https://image.nurdykim.me/gomo/logo-param.png";

	@DisplayName("관심사를 생성한다.")
	@Test
	void create_interest() {
		Interest interest = Interest.of(ID, REGISTRANT_ID, NAME, LOGO_URL);

		assertThat(interest)
			.extracting("id", "registrantId", "name", "logoUrl")
			.containsExactly(ID, REGISTRANT_ID, NAME, LOGO_URL);
	}

	@DisplayName("관심사 로고를 등록하지 않으면 기본 이미지가 등록된다.")
	@Test
	void create_interest_with_default_logo() {
		Interest interest = Interest.of(ID, REGISTRANT_ID, NAME, null);

		assertThat(interest)
			.extracting("id", "registrantId", "name", "logoUrl")
			.containsExactly(ID, REGISTRANT_ID, NAME, "https://image.nurdykim.me/gomo/default-logo.png");
	}

	@DisplayName("관심사 이름을 수정한다.")
	@Test
	void update_interest_name() {
		Interest interest = Interest.of(ID, REGISTRANT_ID, NAME, LOGO_URL);
		InterestName updatedName = InterestName.of("updated interest name");
		interest.updateName(updatedName);

		assertThat(interest)
			.extracting("id", "registrantId", "name", "logoUrl")
			.containsExactly(ID, REGISTRANT_ID, updatedName, LOGO_URL);
	}

	@DisplayName("관심사 이름을 수정한다.")
	@Test
	void update_interest_logo() {
		Interest interest = Interest.of(ID, REGISTRANT_ID, NAME, LOGO_URL);
		interest.updateLogoUrl("https://mini-cloud/updated_logo-param.png");

		assertThat(interest)
			.extracting("id", "registrantId", "name", "logoUrl")
			.containsExactly(ID, REGISTRANT_ID, NAME, "https://mini-cloud/updated_logo-param.png");
	}

	@DisplayName("관심사는 등록한 사람만 접근할 수 있다.")
	@Test
	void access_interest() {
		Interest interest = Interest.of(ID, REGISTRANT_ID, NAME, LOGO_URL);
		interest.validateAuthority(REGISTRANT_ID.getId());
	}

	@DisplayName("관심사를 등록한 사람이 아니면 접근할 수 없다.")
	@Test
	void access_denied_interest() {
		Interest interest = Interest.of(ID, REGISTRANT_ID, NAME, LOGO_URL);

		assertThatThrownBy(() -> interest.validateAuthority(UNAUTHORIZED_ID.getId()))
			.isInstanceOf(InterestAccessDeniedException.class)
			.hasMessageContaining("Access denied for the interest");
	}

	@DisplayName("관심사 숙련도를 향상한다.")
	@Test
	void enhance_interest_proficiency() {
		Interest interest = Interest.of(ID, REGISTRANT_ID, NAME, LOGO_URL);
		interest.enhanceProficiency(10, 400);

		assertThat(interest.getProficiency().getScore().getScore()).isEqualTo(10);
	}
}
