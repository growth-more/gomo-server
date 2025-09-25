package com.gomo.app.core.quest.unit.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.core.quest.domain.model.CompletionProof;
import com.gomo.app.core.quest.exception.CompletionProofConstraintViolationException;
import com.gomo.app.core.quest.exception.code.CompletionProofErrorCode;

@DisplayName("[Domain unit]: 퀘스트 증명 생성 및 수정 테스트")
public class CompletionProofTest {

	private static final String URL = "https://proof";
	private static final String BLANK = "     ";
	private static final String TOO_LONG_URL = Stream.generate(() -> "a").limit(513).collect(Collectors.joining());

	@DisplayName("퀘스트 증명은 기본 값으로 'no_proof' 을 가진다.")
	@Test
	void create_default_completion_proof() {
		CompletionProof completionProof = CompletionProof.createDefault();

		assertThat(completionProof.getUrl()).isEqualTo("no_proof");
	}

	@DisplayName("퀘스트 증명을 생성한다.")
	@Test
	void create_completion_proof() {
		CompletionProof completionProof = CompletionProof.of(URL);

		assertThat(completionProof.toString()).isEqualTo(URL);
	}

	@DisplayName("null 으로 퀘스트 증명을 생성할 수 없다.")
	@Test
	void create_completion_proof_with_null() {
		assertThatThrownBy(() -> CompletionProof.of(null))
			.isInstanceOf(CompletionProofConstraintViolationException.class)
			.hasMessageContaining(CompletionProofErrorCode.BLANK.getMessage());
	}

	@DisplayName("공백으로 퀘스트 증명을 생성할 수 없다.")
	@Test
	void create_completion_proof_with_blank() {
		assertThatThrownBy(() -> CompletionProof.of(BLANK))
			.isInstanceOf(CompletionProofConstraintViolationException.class)
			.hasMessageContaining(CompletionProofErrorCode.BLANK.getMessage());
	}

	@DisplayName("최대 길이보다 길게 퀘스트 증명을 생성할 수 없다.")
	@Test
	void create_completion_proof_with_long_length() {
		assertThatThrownBy(() -> CompletionProof.of(TOO_LONG_URL))
			.isInstanceOf(CompletionProofConstraintViolationException.class)
			.hasMessageContaining(CompletionProofErrorCode.TOO_LONG.getMessage());
	}
}
