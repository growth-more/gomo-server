package com.gomo.app.member.unit;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.quest.domain.model.QuestType;

@DisplayName("[Domain unit]: 회원 테스트")
public class MemberTest {

	@DisplayName("퀘스트 개수가 퀘스트 제한에 도달하지 않는다.")
	@Test
	void not_exceed_quest_threshold() {
		Member member = MemberFixture.member(3);

		boolean actual = member.hasReachedQuestThreshold(QuestType.DAILY.name(), 2);

		assertThat(actual).isFalse();
	}

	@DisplayName("퀘스트 개수가 퀘스트 제한에 도달한다.")
	@Test
	void exceed_quest_threshold() {
		Member member = MemberFixture.member(3);

		boolean actual = member.hasReachedQuestThreshold(QuestType.DAILY.name(), 3);

		assertThat(actual).isTrue();
	}
}
