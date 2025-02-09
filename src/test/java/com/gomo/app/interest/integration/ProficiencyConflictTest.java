package com.gomo.app.interest.integration;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
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
import com.gomo.app.interest.common.dataprovider.InterestDataProvider;
import com.gomo.app.interest.common.util.InterestDataHelper;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.domain.service.ProficiencyService;
import com.gomo.app.interest.exception.ProficiencyAdjustFailureException;

@DisplayName("[Domain integration]: 숙련도 충돌 상황 테스트")
public class ProficiencyConflictTest extends IntegrationTestBase {

	@Autowired
	ProficiencyService sut;

	@Autowired
	InterestRepository interestRepository;

	@Autowired
	InterestDataProvider interestDataProvider;
	Interest backend;
	Interest java;

	@Autowired
	InterestDataHelper interestDataHelper;

	@BeforeEach
	void setUp() {
		backend = interestDataProvider.backend();
		java = interestDataProvider.java();
	}

	@AfterEach
	void tearDown() {
		interestDataHelper.cleanUp();
	}

	@DisplayName("숙련도를 조정할 때, Lost Update 가 발생해도 재시도를 통해 변화량을 반영한다.")
	@Test
	void retry_when_lost_update() {
		ExecutorService executors = Executors.newFixedThreadPool(3);
		List<CompletableFuture<Void>> futures = new ArrayList<>();

		for (int i = 0; i < 3; i++) {
			futures.add(CompletableFuture.supplyAsync(() -> {
				sut.adjust(java.getId(), 20);
				return null;
			}, executors));
		}

		assertDoesNotThrow(() -> {
			futures.forEach(CompletableFuture::join);
		});
	}

	@DisplayName("숙련도를 조정할 때, 세 번의 재시도에도 불구하고 충돌을 해결하지 못하면 변화량을 반영하지 못한다.")
	@Test
	void fill_shouldRetryThreeTimesAndThenFail_dueToOptimisticLocking() {
		ExecutorService executors = Executors.newFixedThreadPool(5);
		List<CompletableFuture<Void>> futures = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			futures.add(CompletableFuture.supplyAsync(() -> {
				sut.adjust(java.getId(), 20);
				return null;
			}, executors));
		}

		Exception exception = assertThrows(CompletionException.class, () -> {
			futures.forEach(CompletableFuture::join);
		});

		assertThat(exception.getCause())
			.isInstanceOf(ProficiencyAdjustFailureException.class)
			.hasMessageContaining("Failed to update proficiency after multiple attempts");
	}
}
