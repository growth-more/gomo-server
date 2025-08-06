package com.gomo.app.point.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gomo.app.point.domain.model.PointWallet;
import com.gomo.app.point.domain.model.PointWalletId;
import com.gomo.app.point.domain.model.TransactorId;

public interface PointWalletRepository extends JpaRepository<PointWallet, PointWalletId> {

	Optional<PointWallet> findByTransactorId(TransactorId transactorId);

	void deletePointWalletByTransactorId(TransactorId transactorId);
}
