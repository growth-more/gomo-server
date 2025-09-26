package com.gomo.app.core.streak.unit.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.streak.domain.model.Achiever;
import com.gomo.app.core.streak.domain.repository.AchieverRepository;
import com.gomo.app.core.streak.domain.service.AchieverService;
import com.gomo.app.core.streak.fixture.AchieverFixture;

@DisplayName("[Domain unit]: 성취자 서비스 테스트")
@ExtendWith(MockitoExtension.class)
public class AchieverServiceTest {

	@InjectMocks
	private AchieverService sut;

	@Mock
	private AchieverRepository achieverRepository;

	@DisplayName("성취자를 조회한다.")
	@Test
	void find_achiever() {
		Achiever achiever = AchieverFixture.achiever(3, 5);
		doReturn(Optional.of(achiever)).when(achieverRepository).findById(any());
		Achiever actual = sut.find(achiever.getId());
		assertThat(actual.getCurrentStreakDays()).isEqualTo(3);
		assertThat(actual.getLongestStreakDays()).isEqualTo(5);
	}
}
