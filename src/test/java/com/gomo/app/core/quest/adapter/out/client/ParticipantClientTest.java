package com.gomo.app.core.quest.adapter.out.client;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.application.port.dto.MemberDto;
import com.gomo.app.core.member.application.port.in.MemberReader;
import com.gomo.app.core.member.fixture.MemberFixture;
import com.gomo.app.core.quest.application.port.dto.ParticipantDto;

@DisplayName("[Infrastructure unit]: MemberDto -> ParticipantDto 전환 테스트")
@ExtendWith(MockitoExtension.class)
class ParticipantClientTest {

	@InjectMocks
	private ParticipantClient sut;

	@Mock
	private MemberReader memberReader;

	@DisplayName("회원의 퀘스트 설정에 따라 퀘스트 할당량이 결정된다.")
	@Test
	void member_to_participant() {
		doReturn(MemberDto.from(MemberFixture.create(5, 6, 7), 1000)).when(memberReader).read(any());
		ParticipantDto dto = sut.read(UUID.randomUUID());
		assertThat(dto)
			.extracting("dailyQuota", "weeklyQuota", "monthlyQuota")
			.containsExactly(5, 6, 7);
	}
}
