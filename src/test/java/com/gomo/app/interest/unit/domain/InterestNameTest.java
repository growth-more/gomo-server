package com.gomo.app.interest.unit.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.common.exception.PolicyViolationException;
import com.gomo.app.interest.domain.model.InterestName;

@DisplayName("[Domain unit]: 관심사 이름 생성 및 수정 테스트")
public class InterestNameTest {

	private static final String NAME_PARAM = "interest name";
	private static final String UPDATED_NAME_PARAM = "updated interest name";
	private static final String TOO_SHORT_NAME = "";
	private static final String TOO_LONG_NAME = Stream.generate(() -> "a").limit(26).collect(Collectors.joining());
	private static final String FORBIDDEN_NAME = "[<>&';|{}[]()`]--*";

	@DisplayName("관심사 이름을 생성한다.")
	@Test
	void create_interest_name() {
		InterestName interestName = InterestName.of(NAME_PARAM);

		assertThat(interestName.toString()).isEqualTo(NAME_PARAM);
	}

	@DisplayName("null을 입력하면 관심사 이름을 생성할 수 없다.")
	@Test
	void create_interest_name_with_null() {
		assertThatThrownBy(() -> InterestName.of(null))
			.isInstanceOf(PolicyViolationException.class)
			.hasMessageContaining("Interest name cannot be null");
	}

	@DisplayName("최소 길이보다 짧은 관심사 이름은 생성할 수 없다.")
	@Test
	void create_interest_name_with_short_name() {
		assertThatThrownBy(() -> InterestName.of(TOO_SHORT_NAME))
			.isInstanceOf(PolicyViolationException.class)
			.hasMessageContaining("Interest name is too short");
	}

	@DisplayName("최대 길이보다 긴 관심사 이름은 생성할 수 없다.")
	@Test
	void create_interest_name_with_long_name() {
		assertThatThrownBy(() -> InterestName.of(TOO_LONG_NAME))
			.isInstanceOf(PolicyViolationException.class)
			.hasMessageContaining("Interest name is too long");
	}

	@DisplayName("금지 문자를 포함한 관심사 이름은 생성할 수 없다.")
	@Test
	void create_interest_name_with_forbidden_characters() throws Exception {
		assertThatThrownBy(() -> InterestName.of(FORBIDDEN_NAME))
			.isInstanceOf(PolicyViolationException.class)
			.hasMessageContaining("Interest name cannot contain forbidden characters");
	}

	@DisplayName("관심사 이름을 수정한다.")
	@Test
	void update_interest_name() {
		InterestName interestName = InterestName.of(NAME_PARAM);
		InterestName updatedInterestName = interestName.update(UPDATED_NAME_PARAM);

		assertThat(updatedInterestName.toString()).isEqualTo(UPDATED_NAME_PARAM);
	}

	@DisplayName("null을 입력하면 관심사 이름을 수정할 수 없다.")
	@Test
	void update_interest_name_with_null() {
		InterestName interestName = InterestName.of(NAME_PARAM);

		assertThatThrownBy(() -> interestName.update(null))
			.isInstanceOf(PolicyViolationException.class)
			.hasMessageContaining("Interest name cannot be null");
	}

	@DisplayName("최소 길이보다 짧은 관심사 이름은 수정할 수 없다.")
	@Test
	void update_interest_name_with_short_name() {
		InterestName interestName = InterestName.of(NAME_PARAM);

		assertThatThrownBy(() -> interestName.update(TOO_SHORT_NAME))
			.isInstanceOf(PolicyViolationException.class)
			.hasMessageContaining("Interest name is too short");
	}

	@DisplayName("최대 길이보다 긴 관심사 이름은 수정할 수 없다.")
	@Test
	void update_interest_name_with_long_name() {
		InterestName interestName = InterestName.of(NAME_PARAM);

		assertThatThrownBy(() -> interestName.update(TOO_LONG_NAME))
			.isInstanceOf(PolicyViolationException.class)
			.hasMessageContaining("Interest name is too long");
	}

	@DisplayName("금지 문자를 포함한 관심사 이름은 수정할 수 없다.")
	@Test
	void update_interest_name_with_forbidden_characters() {
		InterestName interestName = InterestName.of(NAME_PARAM);

		assertThatThrownBy(() -> interestName.update(FORBIDDEN_NAME))
			.isInstanceOf(PolicyViolationException.class)
			.hasMessageContaining("Interest name cannot contain forbidden characters");
	}
}
