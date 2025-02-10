package com.gomo.app.point.domain.service;

import static com.gomo.app.common.exception.DomainErrorCode.*;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.domain.service.DomainService;
import com.gomo.app.common.exception.NotFoundException;
import com.gomo.app.point.domain.model.Balance;
import com.gomo.app.point.domain.model.PointWallet;
import com.gomo.app.point.domain.model.TransactionType;
import com.gomo.app.point.domain.model.TransactorId;
import com.gomo.app.point.domain.repository.PointWalletRepository;
import com.gomo.app.point.exception.BalanceUpdateFailureException;
import com.gomo.app.point.exception.PointWalletErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class PointWalletService {

	private final PointWalletRepository pointWalletRepository;

	@Transactional
	@Retryable(
		value = ObjectOptimisticLockingFailureException.class,
		backoff = @Backoff(delay = 100)
	)
	public void adjustPointBalance(TransactorId transactorId, TransactionType transactionType, int deltaAmount) {
		PointWallet pointWallet = findPointWalletByTransactorId(transactorId);
		pointWallet.adjustBalance(transactionType.getOperationType() * deltaAmount);
	}

	public Balance findBalance(TransactorId transactorId) {
		PointWallet pointWallet = findPointWalletByTransactorId(transactorId);
		return pointWallet.getBalance();
	}

	@Recover
	protected void handleOptimisticLockingFailure(ObjectOptimisticLockingFailureException e) {
		throw new BalanceUpdateFailureException(PointWalletErrorCode.UPDATE_CONFLICT, e);
	}

	private PointWallet findPointWalletByTransactorId(TransactorId transactorId) {
		return pointWalletRepository.findByTransactorId(transactorId)
			.orElseThrow(() -> new NotFoundException(NOT_FOUND, "PointWallet not found with transactor id: " + transactorId.getId()));
	}
}
