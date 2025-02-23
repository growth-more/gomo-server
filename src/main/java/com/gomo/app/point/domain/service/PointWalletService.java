package com.gomo.app.point.domain.service;

import static com.gomo.app.common.exception.DomainErrorCode.*;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.domain.service.DomainService;
import com.gomo.app.common.exception.NotFoundException;
import com.gomo.app.point.domain.model.Balance;
import com.gomo.app.point.domain.model.PointWallet;
import com.gomo.app.point.domain.model.TransactionType;
import com.gomo.app.point.domain.model.TransactorId;
import com.gomo.app.point.domain.repository.PointWalletRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class PointWalletService {

	private final PointWalletRepository pointWalletRepository;

	@Transactional
	public void adjustPointBalance(TransactorId transactorId, TransactionType transactionType, int deltaAmount) {
		PointWallet pointWallet = findPointWalletByTransactorId(transactorId);
		pointWallet.adjustBalance(transactionType.getOperationType() * deltaAmount);
	}

	public Balance findBalance(TransactorId transactorId) {
		PointWallet pointWallet = findPointWalletByTransactorId(transactorId);
		return pointWallet.getBalance();
	}

	private PointWallet findPointWalletByTransactorId(TransactorId transactorId) {
		return pointWalletRepository.findByTransactorId(transactorId)
			.orElseThrow(() -> new NotFoundException(NOT_FOUND, "PointWallet not found with transactor id: " + transactorId.getId()));
	}
}
