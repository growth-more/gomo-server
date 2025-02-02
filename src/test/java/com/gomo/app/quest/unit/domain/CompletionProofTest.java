package com.gomo.app.quest.unit.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.common.exception.PolicyViolationException;
import com.gomo.app.quest.domain.model.CompletionProof;

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

	@DisplayName("퀘스트 증명을 수정한다.")
	@Test
	void update_completion_proof() {
		CompletionProof completionProof = CompletionProof.createDefault();
		CompletionProof updatedCompletionProof = completionProof.update(URL);

		assertThat(updatedCompletionProof.toString()).isEqualTo(URL);
	}

	@DisplayName("null 으로 퀘스트 증명을 수정할 수 없다.")
	@Test
	void update_completion_proof_with_null() {
		CompletionProof completionProof = CompletionProof.createDefault();

		assertThatThrownBy(() -> completionProof.update(null))
			.isInstanceOf(PolicyViolationException.class)
			.hasMessageContaining("Completion proof cannot be blank");
	}

	@DisplayName("공백으로 퀘스트 증명을 수정할 수 없다.")
	@Test
	void update_completion_proof_with_blank() {
		CompletionProof completionProof = CompletionProof.createDefault();

		assertThatThrownBy(() -> completionProof.update(BLANK))
			.isInstanceOf(PolicyViolationException.class)
			.hasMessageContaining("Completion proof cannot be blank");
	}

	@DisplayName("최대 길이보다 길게 퀘스트 증명을 수정할 수 없다.")
	@Test
	void update_completion_proof_with_long_length() {
		CompletionProof completionProof = CompletionProof.createDefault();

		assertThatThrownBy(() -> completionProof.update(TOO_LONG_URL))
			.isInstanceOf(PolicyViolationException.class)
			.hasMessageContaining("Completion proof must not exceed 512 characters");
	}
}
