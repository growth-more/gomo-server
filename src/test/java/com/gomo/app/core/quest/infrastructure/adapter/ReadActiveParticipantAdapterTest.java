package com.gomo.app.core.quest.infrastructure.adapter;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.application.port.ReadActiveMemberPortIn;
import com.gomo.app.core.member.application.port.dto.ActiveMemberDto;
import com.gomo.app.core.member.fixture.MemberFixture;
import com.gomo.app.core.quest.application.port.dto.ActiveParticipantDto;

@DisplayName("[Infrastructure unit]: ActiveMemberDto -> ActiveParticipantDto 전환 테스트")
@ExtendWith(MockitoExtension.class)
class ReadActiveParticipantAdapterTest {

	@InjectMocks
	private ReadActiveParticipantAdapter sut;

	@Mock
	private ReadActiveMemberPortIn readActiveMemberPortIn;

	@DisplayName("활성화 사용자 목록을 활성화 참여자 목록으로 전환한다.")
	@Test
	void interest_to_subject() {
		List<ActiveMemberDto> activeMemberDtos = List.of(
			ActiveMemberDto.from(MemberFixture.create()),
			ActiveMemberDto.from(MemberFixture.create()),
			ActiveMemberDto.from(MemberFixture.create())
		);
		doReturn(activeMemberDtos).when(readActiveMemberPortIn).findAll(any());

		List<ActiveParticipantDto> actual = sut.findAll(LocalDate.now());

		assertThat(actual.size()).isEqualTo(activeMemberDtos.size());
	}

	@DisplayName("관삼사 목록이 없다면 빈 목록을 반환한다.")
	@Test
	void not_found_interests() {
		doReturn(List.of()).when(readActiveMemberPortIn).findAll(any());

		List<ActiveParticipantDto> actual = sut.findAll(LocalDate.now());

		assertThat(actual.isEmpty()).isTrue();
	}
}
