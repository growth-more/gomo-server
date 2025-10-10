package com.gomo.app.core.member.domain.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.core.member.exception.HandleConstraintViolationException;
import com.gomo.app.core.member.exception.code.HandleErrorCode;

@DisplayName("[Domain unit]: 핸들 생성 및 수정 테스트")
public class HandleTest {
	private static final String HANDLE = "@test_handle";
	private static final String BLANK = "   ";
	private static final String TOO_SHORT_HANDLE = "@a";
	private static final String TOO_LONG_HANDLE = "@a".repeat(31);
	private static final String INVALID_HANDLE = "test_handle!";

	@DisplayName("핸들을 생성한다.")
	@Test
	void create_handle() {
		Handle handle = Handle.of(HANDLE);
		assertThat(handle.toString()).isEqualTo(HANDLE);
	}

	@DisplayName("null을 입력하면 핸들은 생성할 수 없다.")
	@Test
	void create_handle_with_null() {
		assertThatThrownBy(() -> Handle.of(null))
			.isInstanceOf(HandleConstraintViolationException.class)
			.hasMessageContaining(HandleErrorCode.BLANK.getMessage());
	}

	@DisplayName("공백만 있는 핸들은 생성할 수 없다.")
	@Test
	void create_handle_with_blank() {
		assertThatThrownBy(() -> Handle.of(BLANK))
			.isInstanceOf(HandleConstraintViolationException.class)
			.hasMessageContaining(HandleErrorCode.BLANK.getMessage());
	}

	@DisplayName("최소 길이보다 짧은 핸들은 생성할 수 없다.")
	@Test
	void create_handle_with_short_length() {
		assertThatThrownBy(() -> Handle.of(TOO_SHORT_HANDLE))
			.isInstanceOf(HandleConstraintViolationException.class)
			.hasMessageContaining(HandleErrorCode.TOO_SHORT.getMessage());
	}

	@DisplayName("최대 길이보다 긴 핸들은 생성할 수 없다.")
	@Test
	void create_handle_with_long_length() {
		assertThatThrownBy(() -> Handle.of(TOO_LONG_HANDLE))
			.isInstanceOf(HandleConstraintViolationException.class)
			.hasMessageContaining(HandleErrorCode.TOO_LONG.getMessage());
	}

	@DisplayName("금지 문자가 포함된 핸들은 생성할 수 없다.")
	@Test
	void create_handle_with_forbidden_handle() {
		assertThatThrownBy(() -> Handle.of(INVALID_HANDLE))
			.isInstanceOf(HandleConstraintViolationException.class)
			.hasMessageContaining(HandleErrorCode.FORBIDDEN.getMessage());
	}

	@DisplayName("핸들을 수정한다.")
	@Test
	void update_handle() {
		Handle handle = Handle.of(HANDLE);
		Handle updatedHandle = handle.update("@updated_handle");
		assertThat(updatedHandle.toString()).isEqualTo("@updated_handle");
	}

	@DisplayName("null을 입력하면 핸들은 수정할 수 없다.")
	@Test
	void update_handle_with_null() {
		Handle handle = Handle.of(HANDLE);
		assertThatThrownBy(() -> handle.update(null))
			.isInstanceOf(HandleConstraintViolationException.class)
			.hasMessageContaining(HandleErrorCode.BLANK.getMessage());
	}

	@DisplayName("공백만 있는 핸들은 수정할 수 없다.")
	@Test
	void update_handle_with_blank() {
		Handle handle = Handle.of(HANDLE);
		assertThatThrownBy(() -> handle.update(BLANK))
			.isInstanceOf(HandleConstraintViolationException.class)
			.hasMessageContaining(HandleErrorCode.BLANK.getMessage());
	}

	@DisplayName("최소 길이보다 짧은 핸들은 수정할 수 없다.")
	@Test
	void update_handle_with_short_length() {
		Handle handle = Handle.of(HANDLE);
		assertThatThrownBy(() -> handle.update(TOO_SHORT_HANDLE))
			.isInstanceOf(HandleConstraintViolationException.class)
			.hasMessageContaining(HandleErrorCode.TOO_SHORT.getMessage());
	}

	@DisplayName("최대 길이보다 긴 핸들은 수정할 수 없다.")
	@Test
	void update_handle_with_long_length() {
		Handle handle = Handle.of(HANDLE);
		assertThatThrownBy(() -> handle.update(TOO_LONG_HANDLE))
			.isInstanceOf(HandleConstraintViolationException.class)
			.hasMessageContaining(HandleErrorCode.TOO_LONG.getMessage());
	}

	@DisplayName("금지 문자가 포함된 핸들은 수정할 수 없다.")
	@Test
	void update_handle_with_forbidden_handle() {
		Handle handle = Handle.of(HANDLE);
		assertThatThrownBy(() -> handle.update(INVALID_HANDLE))
			.isInstanceOf(HandleConstraintViolationException.class)
			.hasMessageContaining(HandleErrorCode.FORBIDDEN.getMessage());
	}

	@DisplayName("핸들이 이미 같은 경우 수정할 수 없다.")
	@Test
	void update_handle_with_duplicated_handle() {
		Handle handle = Handle.of(HANDLE);
		assertThatThrownBy(() -> handle.update(HANDLE))
			.isInstanceOf(HandleConstraintViolationException.class)
			.hasMessageContaining(HandleErrorCode.DUPLICATED.getMessage());
	}
}
