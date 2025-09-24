package com.gomo.app.point.application;

import java.util.UUID;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.point.domain.model.Balance;
import com.gomo.app.point.domain.model.TransactorId;
import com.gomo.app.point.domain.service.PointWalletService;
import com.gomo.app.point.presentation.response.ReadBalanceResponse;

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
