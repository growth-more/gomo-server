package com.gomo.app.point.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gomo.app.point.domain.model.Point;
import com.gomo.app.point.domain.model.PointId;
import com.gomo.app.point.domain.model.TransactorId;

public interface PointRepository extends JpaRepository<Point, PointId> {

	@Query("select p from Point p"
		+ " where p.transactorId = :memberId and p.id > :lastElementId"
		+ " order by cast(p.id as long) desc"
		+ " limit :size")
	List<Point> findAllByTransactorId(
		TransactorId transactorId,
		PointId lastElementId,
		int size
	);
}
