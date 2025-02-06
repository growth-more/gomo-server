package com.gomo.app.streak.integration;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.quest.common.util.StreakDataHelper;
import com.gomo.app.streak.common.dataprovider.StreakDataProvider;
import com.gomo.app.streak.domain.model.Streak;
import com.gomo.app.streak.domain.model.StreakId;
import com.gomo.app.streak.domain.model.StreakType;
import com.gomo.app.streak.domain.service.StreakService;
import com.gomo.app.streak.exception.StreakUpdateFailureException;

@DisplayName("[Domain integration]: 스트릭 충돌 상황 테스트")
public class StreakConflictTest extends IntegrationTestBase {

	@Autowired
	StreakService sut;

	@Autowired
	StreakDataProvider streakDataProvider;
	Streak dailyStreak;

	@Autowired
	StreakDataHelper streakDataHelper;

	@BeforeEach
	void setUp() {
		dailyStreak = streakDataProvider.dailyFirstStreak();
	}

	@AfterEach
	void tearDown() {
		streakDataHelper.cleanUp();
	}

	@DisplayName("완료 퀘스트 개수를 증가시킬 때, Lost Update 가 발생해도 재시도를 통해 증가시킨다.")
	@Test
	void retry_when_lost_update() {
		Streak streak = Streak.of(
			StreakId.of(UUID.randomUUID()),
			dailyStreak.getAchieverId(),
			StreakType.DAILY,
			dailyStreak.getFilledDate(),
			1
		);

		ExecutorService executors = Executors.newFixedThreadPool(3);
		List<CompletableFuture<Void>> futures = new ArrayList<>();

		for (int i = 0; i < 3; i++) {
			futures.add(CompletableFuture.supplyAsync(() -> {
				sut.fill(streak);
				return null;
			}, executors));
		}

		assertDoesNotThrow(() -> {
			futures.forEach(CompletableFuture::join);
		});
	}

	@DisplayName("완료 퀘스트 개수를 증가시킬 때, 세 번의 재시도에도 불구하고 충돌을 해결하지 못하면 개수를 증가시키지 못한다.")
	@Test
	void fill_shouldRetryThreeTimesAndThenFail_dueToOptimisticLocking() {
		Streak streak = Streak.of(
			StreakId.of(UUID.randomUUID()),
			dailyStreak.getAchieverId(),
			StreakType.DAILY,
			dailyStreak.getFilledDate(),
			1
		);

		ExecutorService executors = Executors.newFixedThreadPool(5);
		List<CompletableFuture<Void>> futures = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			futures.add(CompletableFuture.supplyAsync(() -> {
				sut.fill(streak);
				return null;
			}, executors));
		}

		Exception exception = assertThrows(CompletionException.class, () -> {
			futures.forEach(CompletableFuture::join);
		});

		assertThat(exception.getCause())
			.isInstanceOf(StreakUpdateFailureException.class)
			.hasMessageContaining("Failed to update Streak after multiple attempts");
	}
}
