package com.gomo.app.core.streak.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.streak.application.port.dto.AchieverDto;
import com.gomo.app.core.streak.domain.model.Achiever;
import com.gomo.app.core.streak.domain.repository.AchieverRepository;
import com.gomo.app.core.streak.fixture.AchieverFixture;

@DisplayName("[Application unit]: 성취자 생성 테스트")
@ExtendWith(MockitoExtension.class)
class AchieverServiceTest {

	@InjectMocks
	private AchieverService sut;

	@Mock
	private AchieverRepository achieverRepository;

	@DisplayName("성취자를 생성한다.")
	@Test
	void create_achiever() {
		doReturn(false).when(achieverRepository).existsById(any());
		doReturn(AchieverFixture.create()).when(achieverRepository).save(any());

		UUID actual = sut.create(UUID.randomUUID());

		assertThat(actual).isNotNull();
	}

	@DisplayName("성취자를 중복 생성한다.")
	@Test
	void create_duplicated_achiever() {
		doReturn(true).when(achieverRepository).existsById(any());

		assertThatThrownBy(() -> sut.create(UUID.randomUUID())).isInstanceOf(IllegalStateException.class);
	}

	@DisplayName("성취자를 조회한다.")
	@Test
	void read_achiever() {
		Achiever achiever = AchieverFixture.create();
		doReturn(Optional.of(achiever)).when(achieverRepository).findById(any());

		AchieverDto actual = sut.read(UUID.randomUUID());

		assertThat(actual).extracting("id", "longestStreakDays", "currentStreakDays")
			.containsExactly(achiever.getId(), achiever.getLongestStreakDays(), achiever.getCurrentStreakDays());
	}

	@DisplayName("성취자를 조회한다.")
	@Test
	void read_achiever_by_id() {
		Achiever achiever = AchieverFixture.create(3, 5);
		doReturn(Optional.of(achiever)).when(achieverRepository).findById(any());

		Achiever actual = sut.findById(achiever.getId());

		assertThat(actual.getCurrentStreakDays()).isEqualTo(3);
		assertThat(actual.getLongestStreakDays()).isEqualTo(5);
	}
}
