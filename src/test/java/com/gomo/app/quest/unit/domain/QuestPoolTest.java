package com.gomo.app.quest.unit.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.core.quest.domain.model.ProcessingStatus;
import com.gomo.app.core.quest.domain.model.QuestPool;
import com.gomo.app.core.quest.domain.model.QuestPoolId;
import com.gomo.app.core.quest.domain.model.SourceType;
import com.gomo.app.core.quest.exception.QuestAccessDeniedException;
import com.gomo.app.quest.fixture.QuestFixture;

@DisplayName("[Domain unit]: 퀘스트 풀 생성 및 수정 테스트")
class QuestPoolTest {

	@DisplayName("퀘스트 풀을 생성한다.")
	@Test
	void create_quest_pool() {
		QuestPool questPool = QuestPool.of(QuestPoolId.of(UUID.randomUUID()), QuestFixture.quest(), ProcessingStatus.UNUSED, SourceType.AI);

		assertThat(questPool)
			.extracting("processingStatus", "sourceType")
			.containsExactly(ProcessingStatus.UNUSED, SourceType.AI);
	}

	@DisplayName("퀘스트 풀 처리 상태를 수정한다.")
	@Test
	void update_quest_pool_processing_status() {
		QuestPool questPool = QuestPool.of(QuestPoolId.of(UUID.randomUUID()), QuestFixture.quest(), ProcessingStatus.UNUSED, SourceType.AI);
		questPool.updateProcessingStatus(ProcessingStatus.ASSIGNED);
		assertThat(questPool.getProcessingStatus()).isEqualTo(ProcessingStatus.ASSIGNED);
	}

	@DisplayName("퀘스트 참여자는 접근 권한이 있다.")
	@Test
	void check_access_authority_by_participant() {
		UUID participantId = UUID.randomUUID();
		QuestPool questPool = QuestPool.of(QuestPoolId.of(UUID.randomUUID()), QuestFixture.quest(participantId), ProcessingStatus.UNUSED, SourceType.AI);
		assertDoesNotThrow(() -> questPool.validateAuthority(participantId));
	}

	@DisplayName("퀘스트 참여자가 아니면 접근 권한이 없다.")
	@Test
	void check_access_authority_by_non_participant() {
		UUID participantId = UUID.randomUUID();
		QuestPool questPool = QuestPool.of(QuestPoolId.of(UUID.randomUUID()), QuestFixture.quest(participantId), ProcessingStatus.UNUSED, SourceType.AI);

		UUID otherId = UUID.randomUUID();
		assertThatThrownBy(() -> questPool.validateAuthority(otherId)).isInstanceOf(QuestAccessDeniedException.class);
	}
}
