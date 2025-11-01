package com.gomo.app.core.point.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gomo.app.core.point.domain.model.Point;

public interface PointRepository extends JpaRepository<Point, UUID> {

	// TODO [2025-11-01] jhl221123 : 복합 인덱스 추가 후, 성능 테스트 필요
	@Query(value = "select p.* from point p " +
		"where p.transactor_id = UNHEX(REPLACE(:transactorId, '-', '')) " +
		"and (:lastTransactionDateTime is null or " +
		"p.transaction_date_time < :lastTransactionDateTime or " +
		"(p.transaction_date_time = :lastTransactionDateTime and p.id < UNHEX(REPLACE(:lastElementId, '-', '')))) " +
		"order by p.transaction_date_time desc, p.id desc " +
		"limit :size", nativeQuery = true)
	List<Point> findAllByTransactorId(
		@Param("transactorId") String transactorId,
		@Param("lastTransactionDateTime") LocalDateTime lastTransactionDateTime,
		@Param("lastElementId") String lastElementId,
		@Param("size") int size
	);

	@Modifying
	@Query("DELETE FROM Point p WHERE p.transactorId = :transactorId")
	void deleteAllByTransactorId(UUID transactorId);

}
