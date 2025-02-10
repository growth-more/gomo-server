package com.gomo.app.point.integration;

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
import com.gomo.app.point.common.dataprovider.PointWalletDataProvider;
import com.gomo.app.point.domain.model.PointWallet;
import com.gomo.app.point.domain.model.TransactionType;
import com.gomo.app.point.domain.service.PointWalletService;
import com.gomo.app.point.exception.BalanceUpdateFailureException;
import com.gomo.app.quest.common.util.PointDataHelper;

@DisplayName("[Domain integration]: 포인트 잔고 조정 충돌 테스트")
public class PointWalletConflictTest extends IntegrationTestBase {

	@Autowired
	PointWalletService sut;

	@Autowired
	PointWalletDataProvider pointWalletDataProvider;
	PointWallet pointWallet;

	@Autowired
	PointDataHelper pointDataHelper;

	@BeforeEach
	void setUp() {
		pointWallet = pointWalletDataProvider.pointWallet();
	}

	@AfterEach
	void tearDown() {
		pointDataHelper.cleanUp();
	}

	@DisplayName("포인트를 조정할 때, Lost Update 가 발생해도 재시도를 통해 변화량을 반영한다.")
	@Test
	void retry_when_lost_update() {
		ExecutorService executors = Executors.newFixedThreadPool(3);
		List<CompletableFuture<Void>> futures = new ArrayList<>();

		for (int i = 0; i < 3; i++) {
			futures.add(CompletableFuture.supplyAsync(() -> {
				sut.adjustPointBalance(pointWallet.getTransactorId(), TransactionType.GAIN, 20);
				return null;
			}, executors));
		}

		assertDoesNotThrow(() -> {
			futures.forEach(CompletableFuture::join);
		});
	}

	@DisplayName("포인트를 조정할 때, 세 번의 재시도에도 불구하고 충돌을 해결하지 못하면 변화량을 반영하지 못한다.")
	@Test
	void retry_fail_when_lost_update() {
		ExecutorService executors = Executors.newFixedThreadPool(5);
		List<CompletableFuture<Void>> futures = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			futures.add(CompletableFuture.supplyAsync(() -> {
				sut.adjustPointBalance(pointWallet.getTransactorId(), TransactionType.GAIN, 20);
				return null;
			}, executors));
		}

		Exception exception = assertThrows(CompletionException.class, () -> {
			futures.forEach(CompletableFuture::join);
		});

		assertThat(exception.getCause())
			.isInstanceOf(BalanceUpdateFailureException.class)
			.hasMessageContaining("Failed to update point after multiple attempts");
	}
}
