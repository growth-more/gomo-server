package com.gomo.app.core.quest.infrastructure.adapter;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.interest.application.port.ReadInterestPortIn;
import com.gomo.app.core.interest.application.port.dto.InterestDto;
import com.gomo.app.core.interest.fixture.InterestFixture;
import com.gomo.app.core.quest.application.port.dto.SubjectDto;

@DisplayName("[Infrastructure unit]: InterestDto -> SubjectDto 전환 테스트")
@ExtendWith(MockitoExtension.class)
class ReadSubjectAdapterTest {

	@InjectMocks
	private ReadSubjectAdapter sut;

	@Mock
	private ReadInterestPortIn readInterestPortIn;

	@DisplayName("참여자 식별자로 퀘스트 주제 목록을 조회한다.")
	@Test
	void find_all_by_participant_id() {
		List<InterestDto> interestDtos = List.of(
			InterestDto.of(InterestFixture.create(), UUID.randomUUID()),
			InterestDto.of(InterestFixture.create(), UUID.randomUUID()),
			InterestDto.of(InterestFixture.create(), UUID.randomUUID())
		);
		doReturn(interestDtos).when(readInterestPortIn).findAll(any());

		List<SubjectDto> actual = sut.findAll(UUID.randomUUID());

		assertThat(actual.size()).isEqualTo(interestDtos.size());
	}

	@DisplayName("참여자 식별자로 조회한 목록이 비었다면 빈 목록을 반환한다.")
	@Test
	void find_all_by_participant_id_with_empty_list() {
		doReturn(List.of()).when(readInterestPortIn).findAll(any());

		List<SubjectDto> actual = sut.findAll(UUID.randomUUID());

		assertThat(actual.isEmpty()).isTrue();
	}

	@DisplayName("참여자 식별자 목록으로 퀘스트 주제 목록을 조회한다.")
	@Test
	void find_all_by_participant_ids() {
		List<InterestDto> interestDtos = List.of(
			InterestDto.of(InterestFixture.create(), UUID.randomUUID()),
			InterestDto.of(InterestFixture.create(), UUID.randomUUID()),
			InterestDto.of(InterestFixture.create(), UUID.randomUUID())
		);
		doReturn(interestDtos).when(readInterestPortIn).findAllByRegistrantIds(any());

		List<SubjectDto> actual = sut.findAllByParticipantIds(Set.of(UUID.randomUUID(), UUID.randomUUID()));

		assertThat(actual.size()).isEqualTo(interestDtos.size());
	}

	@DisplayName("참여자 식별자 목록으로 조회한 목록이 비었다면 빈 목록을 반환한다.")
	@Test
	void find_all_by_participant_ids_with_empty_list() {
		doReturn(List.of()).when(readInterestPortIn).findAllByRegistrantIds(any());

		List<SubjectDto> actual = sut.findAllByParticipantIds(Set.of(UUID.randomUUID(), UUID.randomUUID()));

		assertThat(actual.isEmpty()).isTrue();
	}
}
