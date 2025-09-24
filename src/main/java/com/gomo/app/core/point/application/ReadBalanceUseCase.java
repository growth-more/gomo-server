package com.gomo.app.core.point.application;

import java.util.UUID;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.core.point.domain.model.Balance;
import com.gomo.app.core.point.domain.model.TransactorId;
import com.gomo.app.core.point.domain.service.PointWalletService;
import com.gomo.app.core.point.presentation.response.ReadBalanceResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadBalanceUseCase {

	private final PointWalletService pointWalletService;

	public ReadBalanceResponse find(UUID transactorId) {
		Balance balance = pointWalletService.findBalance(TransactorId.of(transactorId));
		return ReadBalanceResponse.of(balance);
	}
}
