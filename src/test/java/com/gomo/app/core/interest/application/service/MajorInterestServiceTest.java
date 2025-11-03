package com.gomo.app.core.interest.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.common.displayorder.OrderChanger;
import com.gomo.app.common.displayorder.UpdatedOrderDto;
import com.gomo.app.core.interest.application.port.command.OrderUpdateMajorInterestCommand;
import com.gomo.app.core.interest.application.port.dto.MajorInterestDto;
import com.gomo.app.core.interest.domain.exception.InterestAccessDeniedException;
import com.gomo.app.core.interest.domain.exception.MajorInterestAccessDeniedException;
import com.gomo.app.core.interest.domain.exception.MajorInterestDuplicatedException;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.MajorInterest;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.core.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.core.interest.fixture.InterestFixture;
import com.gomo.app.core.interest.fixture.MajorInterestFixture;

@DisplayName("[Application unit]: 주요 관심사 서비스 테스트")
@ExtendWith(MockitoExtension.class)
public class MajorInterestServiceTest {

	@InjectMocks
	private MajorInterestService sut;

	@Mock
	private InterestService interestService;

	@Mock
	private MajorInterestRepository majorInterestRepository;

	@Mock
	private InterestRepository interestRepository;

	@DisplayName("주요 관심사를 등록한다.")
	@Test
	void create_major_interest() {
		MajorInterest majorInterest = MajorInterestFixture.create();
		doReturn(InterestFixture.create(majorInterest.getRegistrantId())).when(interestService).readById(any());
		doReturn(4).when(majorInterestRepository).findMaxDisplayOrder(any());
		doReturn(majorInterest).when(majorInterestRepository).save(any());

		UUID actual = sut.create(majorInterest.getRegistrantId(), majorInterest.getInterestId());

		assertThat(actual).isEqualTo(majorInterest.getId());
	}

	@DisplayName("권한 없는 접근자는 주요 관심사를 등록할 수 없다.")
	@Test
	void create_major_interest_by_unauthorized_accessor() {
		Interest interest = mock(Interest.class);
		doReturn(interest).when(interestService).readById(any());
		doThrow(InterestAccessDeniedException.class).when(interest).validateAuthority(any(UUID.class));

		assertThatThrownBy(() -> sut.create(UUID.randomUUID(), UUID.randomUUID())).isInstanceOf(InterestAccessDeniedException.class);
	}

	@DisplayName("이미 주요 관심사로 등록된 관심사는 중복해서 등록할 수 없다.")
	@Test
	void create_major_interest_with_already_existing_major_interest() {
		UUID registrantId = UUID.randomUUID();
		doReturn(InterestFixture.create(registrantId)).when(interestService).readById(any());
		doThrow(MajorInterestDuplicatedException.class).when(majorInterestRepository).findByInterestId(any());

		assertThatThrownBy(() -> sut.create(registrantId, UUID.randomUUID()))
			.isInstanceOf(MajorInterestDuplicatedException.class);
	}

	@DisplayName("주요 관심사 목록을 정렬 순서에 맞게 조회한다.")
	@Test
	void read_interest_by_display_order() {
		MajorInterest expected1 = MajorInterestFixture.create();
		MajorInterest expected2 = MajorInterestFixture.create();
		List<Interest> interests = getInterests(expected1, expected2);
		doReturn(interests).when(interestRepository).findAllById(any());
		doReturn(List.of(expected1, expected2)).when(majorInterestRepository).findAllByRegistrantIdOrderByDisplayOrder(any());

		List<MajorInterestDto> actual = sut.readAll(expected1.getRegistrantId());

		assertThat(actual)
			.hasSize(2)
			.extracting("id", "interestId", "name")
			.containsExactly(
				tuple(expected1.getId(), expected1.getInterestId(), "interest1"),
				tuple(expected2.getId(), expected2.getInterestId(), "interest2")
			);
	}

	private List<Interest> getInterests(MajorInterest expected1, MajorInterest expected2) {
		return List.of(
			InterestFixture.create(expected1.getInterestId(), UUID.randomUUID(), "interest1"),
			InterestFixture.create(expected2.getInterestId(), UUID.randomUUID(), "interest2")
		);
	}

	@DisplayName("주요 관심사를 조회한다.")
	@Test
	void read_major_interest() {
		MajorInterest majorInterest = MajorInterestFixture.create();
		doReturn(Optional.of(majorInterest)).when(majorInterestRepository).findById(any());

		MajorInterest actual = sut.readById(majorInterest.getId());

		assertThat(actual).isEqualTo(majorInterest);
	}

	@DisplayName("주요 관심사를 삭제한다.")
	@Test
	void delete_interest() {
		MajorInterest majorInterest = MajorInterestFixture.create();
		doReturn(Optional.of(majorInterest)).when(majorInterestRepository).findById(any());

		sut.delete(majorInterest.getRegistrantId(), majorInterest.getId());

		verify(majorInterestRepository, times(1)).delete(any(MajorInterest.class));
	}

	@DisplayName("권한 없는 접근자는 주요 관심사를 삭제할 수 없다.")
	@Test
	void delete_interest_by_unauthorized_accessor() {
		MajorInterest majorInterest = mock(MajorInterest.class);
		doThrow(MajorInterestAccessDeniedException.class).when(majorInterest).validateAuthority(any(UUID.class));
		doReturn(Optional.of(majorInterest)).when(majorInterestRepository).findById(any());

		assertThatThrownBy(() -> sut.delete(UUID.randomUUID(), UUID.randomUUID()))
			.isInstanceOf(MajorInterestAccessDeniedException.class);
	}

	@DisplayName("주요 관심사 정렬 순서를 변경한다.")
	@Test
	void update_interest_display_order() {
		doReturn(getMajorInterests()).when(majorInterestRepository).findAllByRegistrantIdOrderByDisplayOrder(any());

		try (MockedStatic<OrderChanger> mockedOrderChanger = mockStatic(OrderChanger.class)) {
			sut.update(OrderUpdateMajorInterestCommand.of(
					UUID.randomUUID(),
					List.of(
						UpdatedOrderDto.of(UUID.randomUUID(), 1),
						UpdatedOrderDto.of(UUID.randomUUID(), 2),
						UpdatedOrderDto.of(UUID.randomUUID(), 3)
					)
				)
			);

			verify(majorInterestRepository, times(1)).findAllByRegistrantIdOrderByDisplayOrder(any());
			mockedOrderChanger.verify(() -> OrderChanger.change(any()), times(1));
		}
	}

	private @NotNull List<MajorInterest> getMajorInterests() {
		return List.of(
			MajorInterestFixture.create(),
			MajorInterestFixture.create(),
			MajorInterestFixture.create()
		);
	}
}
