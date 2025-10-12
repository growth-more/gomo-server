package com.gomo.app.core.interest.domain.model;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.gomo.app.core.interest.exception.InterestAccessDeniedException;

@DisplayName("[Domain unit]: 관심사 생성 및 수정 테스트")
public class InterestTest {

	private static final UUID ID = UUID.randomUUID();
	private static final UUID REGISTRANT_ID = UUID.randomUUID();
	private static final UUID UNAUTHORIZED_ID = UUID.randomUUID();
	private static final InterestName NAME = InterestName.of("interest name");
	private static final Logo LOGO = Logo.of("https://image.nurdykim.me/gomo/logo-param.png");
	private static final String COLOR_CODE = "#0000FF";

	@DisplayName("관심사를 생성한다.")
	@Test
	void create_interest() {
		Interest interest = Interest.of(ID, REGISTRANT_ID, NAME, LOGO, COLOR_CODE);

		assertThat(interest)
			.extracting("id", "registrantId", "name", "logo", "colorCode")
			.containsExactly(ID, REGISTRANT_ID, NAME, LOGO, COLOR_CODE);
	}

	@DisplayName("관심사 로고를 등록하지 않으면 null이 입력된다.")
	@Test
	void create_interest_with_default_logo() {
		Interest interest = Interest.of(ID, REGISTRANT_ID, NAME, null, COLOR_CODE);

		assertThat(interest)
			.extracting("id", "registrantId", "name", "logo", "colorCode")
			.containsExactly(ID, REGISTRANT_ID, NAME, null, COLOR_CODE);
	}

	@DisplayName("관심사 이름을 수정한다.")
	@Test
	void update_interest_name() {
		Interest interest = Interest.of(ID, REGISTRANT_ID, NAME, LOGO, COLOR_CODE);
		InterestName updatedName = InterestName.of("updated interest name");
		interest.updateName(updatedName);

		assertThat(interest)
			.extracting("id", "registrantId", "name", "logo", "colorCode")
			.containsExactly(ID, REGISTRANT_ID, updatedName, LOGO, COLOR_CODE);
	}

	@DisplayName("관심사 색상 코드를 수정한다.")
	@Test
	void update_interest_color_code() {
		Interest interest = Interest.of(ID, REGISTRANT_ID, NAME, LOGO, COLOR_CODE);
		interest.updateColorCode("#FF0000");

		assertThat(interest)
			.extracting("id", "registrantId", "name", "logo", "colorCode")
			.containsExactly(ID, REGISTRANT_ID, NAME, LOGO, "#FF0000");
	}

	@DisplayName("관심사 로고 주소를 수정한다.")
	@Test
	void update_interest_logo() {
		Interest interest = Interest.of(ID, REGISTRANT_ID, NAME, LOGO, COLOR_CODE);
		Logo updatedLogo = Logo.of("https://mini-cloud/updated_logo-param.png");
		interest.updateLogo(updatedLogo);

		assertThat(interest)
			.extracting("id", "registrantId", "name", "logo", "colorCode")
			.containsExactly(ID, REGISTRANT_ID, NAME, updatedLogo, COLOR_CODE);
	}

	@DisplayName("관심사의 로고가 기본 로고인지 확인한다.")
	@Test
	void has_default_logo() {
		Interest interest = Interest.of(ID, REGISTRANT_ID, NAME, Logo.of(null), COLOR_CODE);

		assertThat(interest.hasDefaultLogo()).isTrue();
	}

	@DisplayName("관심사의 로고가 기본 로고가 아님을 확인한다.")
	@Test
	void does_not_have_default_logo() {
		Interest interest = Interest.of(ID, REGISTRANT_ID, NAME, LOGO, COLOR_CODE);

		assertThat(interest.hasDefaultLogo()).isFalse();
	}

	@DisplayName("관심사는 등록한 사람만 접근할 수 있다.")
	@Test
	void access_interest() {
		Interest interest = Interest.of(ID, REGISTRANT_ID, NAME, LOGO, COLOR_CODE);
		interest.validateAuthority(REGISTRANT_ID);
	}

	@DisplayName("관심사를 등록한 사람이 아니면 접근할 수 없다.")
	@Test
	void access_denied_interest() {
		Interest interest = Interest.of(ID, REGISTRANT_ID, NAME, LOGO, COLOR_CODE);

		assertThatThrownBy(() -> interest.validateAuthority(UNAUTHORIZED_ID))
			.isInstanceOf(InterestAccessDeniedException.class)
			.hasMessageContaining("Access denied for the interest");
	}

	@DisplayName("관심사 숙련도를 향상한다.")
	@Test
	void enhance_interest_proficiency() {
		Proficiency mockProficiency = mock(Proficiency.class);
		Interest interest = new Interest(ID, REGISTRANT_ID, mockProficiency, NAME, LOGO, COLOR_CODE);
		interest.adjustProficiency(10, Mockito.mock(ProficiencyCalculator.class));

		verify(mockProficiency, times(1)).adjust(anyInt(), any(ProficiencyCalculator.class));
	}
}
