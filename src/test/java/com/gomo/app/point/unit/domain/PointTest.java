package com.gomo.app.point.unit.domain;

import static com.gomo.app.point.domain.model.SourceType.*;
import static com.gomo.app.point.domain.model.TransactionType.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.point.domain.model.Point;
import com.gomo.app.point.domain.model.PointId;
import com.gomo.app.point.domain.model.TransactorId;
import com.gomo.app.point.exception.InvalidPointAmountException;

@DisplayName("[Domain unit]: 포인트 생성 테스트")
public class PointTest {

	private static final PointId POINT_ID = PointId.of(UUID.randomUUID());
	private static final TransactorId TRANSACTOR_ID = TransactorId.of(UUID.randomUUID());

	@DisplayName("포인트를 생성한다.")
	@Test
	void create_point() {
		LocalDateTime now = LocalDateTime.now();
		Point point = Point.of(POINT_ID, TRANSACTOR_ID, QUEST, GAIN, 10, QUEST.getDescription() + GAIN.getDescription(), now);

		assertThat(point)
			.extracting("id", "transactorId", "sourceType", "transactionType", "amount", "description", "transactionDateTime")
			.containsExactly(POINT_ID, TRANSACTOR_ID, QUEST, GAIN, 10, QUEST.getDescription() + GAIN.getDescription(), now);
	}

	@DisplayName("포인트 양은 음수일 수 없다.")
	@Test
	void create_negative_point() {
		assertThatThrownBy(() -> Point.of(POINT_ID, TRANSACTOR_ID, QUEST, GAIN, -10, QUEST.getDescription() + GAIN.getDescription(), LocalDateTime.now()))
			.isInstanceOf(InvalidPointAmountException.class)
			.hasMessageContaining("Point amount not allowed negative value");
	}
}
