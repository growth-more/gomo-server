package com.gomo.app.core.point.application.service;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.core.point.application.port.in.PointWalletCreator;
import com.gomo.app.core.point.domain.exception.PointWalletNotFoundException;
import com.gomo.app.core.point.domain.exception.code.PointWalletErrorCode;
import com.gomo.app.core.point.domain.model.Balance;
import com.gomo.app.core.point.domain.model.PointWallet;
import com.gomo.app.core.point.domain.model.TransactionType;
import com.gomo.app.core.point.domain.repository.PointWalletRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class PointWalletService implements PointWalletCreator {

	private final PointWalletRepository pointWalletRepository;

	@Override
	@AuditLog(action = "CREATE_POINT_WALLET")
	public UUID create(UUID transactorId) {
		ensureNotDuplicated(transactorId);
		PointWallet pointWallet = PointWallet.createDefault(UUIDGenerator.generate(), transactorId);
		PointWallet savedPointWallet = pointWalletRepository.save(pointWallet);
		return savedPointWallet.getId();
	}

	private void ensureNotDuplicated(UUID transactorId) {
		if (pointWalletRepository.existsByTransactorId(transactorId)) {
			throw new IllegalStateException("Point wallet already exists with transactorId " + transactorId);
		}
	}

	void adjustPointBalance(UUID transactorId, TransactionType transactionType, int deltaAmount) {
		PointWallet pointWallet = readByTransactorId(transactorId);
		pointWallet.adjustBalance(transactionType.getOperationType() * deltaAmount);
	}

	Balance readBalance(UUID transactorId) {
		PointWallet pointWallet = readByTransactorId(transactorId);
		return pointWallet.getBalance();
	}

	PointWallet readByTransactorId(UUID transactorId) {
		return pointWalletRepository.findByTransactorId(transactorId)
			.orElseThrow(() -> new PointWalletNotFoundException(PointWalletErrorCode.NOT_FOUND));
	}
}
