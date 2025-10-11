package com.gomo.app.core.interest.domain.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.interest.domain.model.MajorInterest;
import com.gomo.app.core.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.core.interest.exception.MajorInterestDuplicatedException;
import com.gomo.app.core.interest.fixture.InterestFixture;
import com.gomo.app.core.interest.fixture.MajorInterestFixture;

@DisplayName("[Domain unit]: 주요 관심사 생성 테스트")
@ExtendWith(MockitoExtension.class)
public class MajorInterestServiceTest {

	@InjectMocks
	private MajorInterestService sut;

	@Mock
	private MajorInterestRepository majorInterestRepository;

	@DisplayName("주요 관심사를 생성한다.")
	@Test
	void create_major_interest() {
		MajorInterest majorInterest = MajorInterestFixture.create();

		doReturn(4).when(majorInterestRepository).findMaxDisplayOrder(any());
		doReturn(majorInterest).when(majorInterestRepository).save(any());

		MajorInterest actual = sut.create(InterestFixture.create());

		assertThat(actual.getId()).isEqualTo(majorInterest.getId());
	}

	@DisplayName("이미 주요 관심사로 등록된 관심사는 중복해서 등록할 수 없다.")
	@Test
	void create_major_interest_with_already_existing_major_interest() {
		doThrow(MajorInterestDuplicatedException.class).when(majorInterestRepository).findByInterestId(any());

		assertThatThrownBy(() -> sut.create(InterestFixture.create()))
			.isInstanceOf(MajorInterestDuplicatedException.class);
	}

	@DisplayName("새로 생성된 반복 퀘스트의 정렬 순서는 현재 등록된 반복 퀘스트의 마지막 번호 + 1이다.")
	@Test
	void create_major_interest_with_display_order() {
		int maxDisplayOrder = 4;

		doReturn(maxDisplayOrder).when(majorInterestRepository).findMaxDisplayOrder(any());
		doReturn(MajorInterestFixture.create(maxDisplayOrder + 1)).when(majorInterestRepository).save(any());

		MajorInterest actual = sut.create(InterestFixture.create());

		assertThat(actual.getDisplayOrder().getDisplayOrder()).isEqualTo(maxDisplayOrder + 1);
	}
}
