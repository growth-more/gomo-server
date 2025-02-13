package com.gomo.app.point.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gomo.app.point.domain.model.Point;
import com.gomo.app.point.domain.model.PointId;

public interface PointRepository extends JpaRepository<Point, PointId> {

	@Query(value = "select p.* from point p " +
		"where p.transactor_id = UNHEX(REPLACE(:transactorId, '-', '')) " +
		"and (UNHEX(REPLACE(:lastElementId, '-', '')) is null " +
		"or p.id < UNHEX(REPLACE(:lastElementId, '-', ''))) " +
		"order by p.transaction_date_time desc " +
		"limit :size", nativeQuery = true)
	List<Point> findAllByTransactorId(
		@Param("transactorId") String transactorId,
		@Param("lastElementId") String lastElementId,
		@Param("size") int size
	);
}
