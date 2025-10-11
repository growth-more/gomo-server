package com.gomo.app.core.point.domain.service;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.DomainService;
import com.gomo.app.core.point.domain.model.Balance;
import com.gomo.app.core.point.domain.model.PointWallet;
import com.gomo.app.core.point.domain.model.TransactionType;
import com.gomo.app.core.point.domain.repository.PointWalletRepository;
import com.gomo.app.core.point.exception.PointWalletNotFoundException;
import com.gomo.app.core.point.exception.code.PointWalletErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class PointWalletService {

	private final PointWalletRepository pointWalletRepository;

	@Transactional
	public void adjustPointBalance(UUID transactorId, TransactionType transactionType, int deltaAmount) {
		PointWallet pointWallet = findPointWalletByTransactorId(transactorId);
		pointWallet.adjustBalance(transactionType.getOperationType() * deltaAmount);
	}

	public Balance findBalance(UUID transactorId) {
		PointWallet pointWallet = findPointWalletByTransactorId(transactorId);
		return pointWallet.getBalance();
	}

	private PointWallet findPointWalletByTransactorId(UUID transactorId) {
		return pointWalletRepository.findByTransactorId(transactorId)
			.orElseThrow(() -> new PointWalletNotFoundException(PointWalletErrorCode.NOT_FOUND));
	}
}
