package com.gomo.app.point.common.fixture;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.point.domain.model.Point;
import com.gomo.app.point.domain.model.PointId;
import com.gomo.app.point.domain.model.SourceType;
import com.gomo.app.point.domain.model.TransactionType;
import com.gomo.app.point.domain.model.TransactorId;

public class PointFixture {

	public static Point point() {
		return Point.of(
			PointId.of(UUID.randomUUID()),
			TransactorId.of(UUID.randomUUID()),
			SourceType.QUEST,
			TransactionType.GAIN,
			10,
			SourceType.QUEST.getDescription() + TransactionType.GAIN.getDescription(),
			LocalDateTime.now()
		);
	}
}
