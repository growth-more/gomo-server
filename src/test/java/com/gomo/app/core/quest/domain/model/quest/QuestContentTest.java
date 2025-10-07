package com.gomo.app.core.quest.domain.model.quest;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.core.quest.exception.QuestContentConstraintViolationException;
import com.gomo.app.core.quest.exception.code.QuestContentErrorCode;

@DisplayName("[Domain unit]: 퀘스트 내용 생성 및 수정 테스트")
public class QuestContentTest {

	private static final String QUEST_CONTENT = "quest content";
	private static final String BLANK = "     ";
	private static final String TOO_SHORT_NAME = "a";
	private static final String TOO_LONG_NAME = Stream.generate(() -> "a").limit(31).collect(Collectors.joining());
	private static final String FORBIDDEN_NAME = "[<>&';|{}[]()`]--*";

	@DisplayName("퀘스트 내용을 생성한다.")
	@Test
	void create_quest_content() {
		QuestContent questContent = QuestContent.of(QUEST_CONTENT);

		assertThat(questContent.toString()).isEqualTo(QUEST_CONTENT);
	}

	@DisplayName("null을 입력하면 퀘스트 내용은 생성할 수 없다.")
	@Test
	void create_quest_content_with_null() {
		assertThatThrownBy(() -> QuestContent.of(null))
			.isInstanceOf(QuestContentConstraintViolationException.class)
			.hasMessageContaining(QuestContentErrorCode.BLANK.getMessage());
	}

	@DisplayName("공백만 있는 퀘스트 내용은 생성할 수 없다.")
	@Test
	void create_quest_content_with_only_blank() {
		assertThatThrownBy(() -> QuestContent.of(BLANK))
			.isInstanceOf(QuestContentConstraintViolationException.class)
			.hasMessageContaining(QuestContentErrorCode.BLANK.getMessage());
	}

	@DisplayName("최소 길이보다 짧은 퀘스트 내용은 생성할 수 없다.")
	@Test
	void create_quest_content_with_short_length() {
		assertThatThrownBy(() -> QuestContent.of(TOO_SHORT_NAME))
			.isInstanceOf(QuestContentConstraintViolationException.class)
			.hasMessageContaining(QuestContentErrorCode.TOO_SHORT.getMessage());
	}

	@DisplayName("최대 길이보다 긴 퀘스트 내용은 생성할 수 없다.")
	@Test
	void create_quest_content_with_long_length() {
		assertThatThrownBy(() -> QuestContent.of(TOO_LONG_NAME))
			.isInstanceOf(QuestContentConstraintViolationException.class)
			.hasMessageContaining(QuestContentErrorCode.TOO_LONG.getMessage());
	}

	@DisplayName("금지 문자를 포함한 관심사 이름은 생성할 수 없다.")
	@Test
	void create_quest_content_with_forbidden_characters() throws Exception {
		assertThatThrownBy(() -> QuestContent.of(FORBIDDEN_NAME))
			.isInstanceOf(QuestContentConstraintViolationException.class)
			.hasMessageContaining(QuestContentErrorCode.FORBIDDEN.getMessage());
	}

	@DisplayName("퀘스트 내용을 수정한다.")
	@Test
	void update_quest_content() {
		QuestContent questContent = QuestContent.of(QUEST_CONTENT);
		QuestContent updatedContent = questContent.update("updated content");

		assertThat(updatedContent.toString()).isEqualTo("updated content");
	}

	@DisplayName("null을 입력하면 퀘스트 내용은 수정할 수 없다.")
	@Test
	void update_quest_content_with_null() {
		QuestContent questContent = QuestContent.of(QUEST_CONTENT);

		assertThatThrownBy(() -> questContent.update(null))
			.isInstanceOf(QuestContentConstraintViolationException.class)
			.hasMessageContaining(QuestContentErrorCode.BLANK.getMessage());
	}

	@DisplayName("공백만 있는 퀘스트 내용은 수정할 수 없다.")
	@Test
	void update_quest_content_with_only_blank() {
		QuestContent questContent = QuestContent.of(QUEST_CONTENT);

		assertThatThrownBy(() -> questContent.update(BLANK))
			.isInstanceOf(QuestContentConstraintViolationException.class)
			.hasMessageContaining(QuestContentErrorCode.BLANK.getMessage());
	}

	@DisplayName("최소 길이보다 짧은 퀘스트 내용은 수정할 수 없다.")
	@Test
	void update_quest_content_with_short_length() {
		QuestContent questContent = QuestContent.of(QUEST_CONTENT);

		assertThatThrownBy(() -> questContent.update(TOO_SHORT_NAME))
			.isInstanceOf(QuestContentConstraintViolationException.class)
			.hasMessageContaining(QuestContentErrorCode.TOO_SHORT.getMessage());
	}

	@DisplayName("최대 길이보다 긴 퀘스트 내용은 수정할 수 없다.")
	@Test
	void update_quest_content_with_long_length() {
		QuestContent questContent = QuestContent.of(QUEST_CONTENT);

		assertThatThrownBy(() -> questContent.update(TOO_LONG_NAME))
			.isInstanceOf(QuestContentConstraintViolationException.class)
			.hasMessageContaining(QuestContentErrorCode.TOO_LONG.getMessage());
	}

	@DisplayName("금지 문자를 포함한 관심사 이름은 수정할 수 없다.")
	@Test
	void update_quest_content_with_forbidden_characters() {
		QuestContent questContent = QuestContent.of(QUEST_CONTENT);

		assertThatThrownBy(() -> questContent.update(FORBIDDEN_NAME))
			.isInstanceOf(QuestContentConstraintViolationException.class)
			.hasMessageContaining(QuestContentErrorCode.FORBIDDEN.getMessage());
	}
}
