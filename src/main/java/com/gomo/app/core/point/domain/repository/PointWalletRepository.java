package com.gomo.app.core.point.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gomo.app.core.point.domain.model.PointWallet;

public interface PointWalletRepository extends JpaRepository<PointWallet, UUID> {

	Optional<PointWallet> findByTransactorId(UUID transactorId);

	void deletePointWalletByTransactorId(UUID transactorId);

	boolean existsByTransactorId(UUID transactorId);
}
