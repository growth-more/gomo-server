package com.gomo.app.interest.unit.domain;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.interest.common.fixture.InterestFixture;
import com.gomo.app.interest.common.fixture.MajorInterestFixture;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.MajorInterest;
import com.gomo.app.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.interest.domain.service.MajorInterestService;
import com.gomo.app.interest.exception.MajorInterestDuplicatedException;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Domain unit]: 주요 관심사 생성 테스트")
public class MajorInterestServiceTest {

	@InjectMocks
	MajorInterestService sut;

	@Mock
	MajorInterestRepository majorInterestRepository;

	@DisplayName("주요 관심사를 생성한다.")
	@Test
	void create_major_interest() {
		MajorInterest majorInterest = MajorInterestFixture.majorInterest();

		doReturn(4).when(majorInterestRepository).findMaxDisplayOrder(any());
		doReturn(majorInterest).when(majorInterestRepository).save(any());

		MajorInterest actual = sut.create(InterestFixture.interest());

		assertThat(actual.getId()).isEqualTo(majorInterest.getId());
	}

	@DisplayName("이미 주요 관심사로 등록된 관심사는 중복해서 등록할 수 없다.")
	@Test
	void create_major_interest_with_already_existing_major_interest() {
		doThrow(MajorInterestDuplicatedException.class).when(majorInterestRepository).findByInterestId(any(InterestId.class));

		assertThatThrownBy(() -> sut.create(InterestFixture.interest()))
			.isInstanceOf(MajorInterestDuplicatedException.class);
	}

	@DisplayName("새로 생성된 반복 퀘스트의 정렬 순서는 현재 등록된 반복 퀘스트의 마지막 번호 + 1이다.")
	@Test
	void create_major_interest_with_display_order() {
		int maxDisplayOrder = 4;

		doReturn(maxDisplayOrder).when(majorInterestRepository).findMaxDisplayOrder(any());
		doReturn(MajorInterestFixture.majorInterest(maxDisplayOrder + 1)).when(majorInterestRepository).save(any());

		MajorInterest actual = sut.create(InterestFixture.interest());

		assertThat(actual.getDisplayOrder().getDisplayOrder()).isEqualTo(maxDisplayOrder + 1);
	}
}
