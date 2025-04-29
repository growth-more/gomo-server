package com.gomo.app.quest.unit.usecase;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.quest.application.translator.ParticipantTranslator;
import com.gomo.app.quest.domain.model.Participant;

@DisplayName("[Application unit]: Member -> Participant 전환 테스트")
@ExtendWith(MockitoExtension.class)
public class ParticipantTranslatorTest {

	@DisplayName("회원의 퀘스트 설정에 따라 퀘스트 할당량이 결정된다.")
	@Test
	void free_to_free() {
		Member member = MemberFixture.member(5, 5, 5);
		Participant participant = ParticipantTranslator.from(member);

		assertThat(participant.getQuestQuota())
			.extracting("dailyQuota", "weeklyQuota", "monthlyQuota")
			.containsExactly(5, 5, 5);
	}
}
