package com.gomo.app.core.point.application.service;

import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.point.application.port.in.BalanceReader;
import com.gomo.app.core.point.domain.model.Balance;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class BalanceService implements BalanceReader {

	private final PointWalletService pointWalletService;

	@Override
	public int find(UUID transactorId) {
		Balance balance = pointWalletService.findBalance(transactorId);
		return balance.getAmount();
	}
}
