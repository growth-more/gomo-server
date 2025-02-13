package com.gomo.app.point.integration;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.point.common.dataprovider.PointDataProvider;
import com.gomo.app.point.domain.model.Point;
import com.gomo.app.point.domain.repository.PointRepository;

@DisplayName("[Domain integration]: 포인트 DB 접근 테스트")
public class PointRepositoryTest extends IntegrationTestBase {

	@Autowired
	PointRepository sut;

	@Autowired
	private PointDataProvider pointDataProvider;
	Point dailyQuestPoint;
	Point weeklyQuestPoint;
	Point monthlyQuestPoint;

	@BeforeEach
	public void setUp() {
		dailyQuestPoint = pointDataProvider.dailyQuest();
		weeklyQuestPoint = pointDataProvider.weeklyQuest();
		monthlyQuestPoint = pointDataProvider.monthlyQuest();
	}

	@DisplayName("마지막 아이디 없이 포인트 목록을 조회한다.")
	@Test
	void find_all_point() {
		List<Point> actual = sut.findAllByTransactorId(
			dailyQuestPoint.getTransactorId().toString(),
			null,
			10
		);

		assertThat(actual.size()).isEqualTo(3);
	}

	@DisplayName("포인트 목록은 거래일자 기준으로 내림차순 조회한다.")
	@Test
	void find_all_point_transaction_date_desc() {
		List<Point> actual = sut.findAllByTransactorId(
			dailyQuestPoint.getTransactorId().toString(),
			null,
			10
		);

		assertThat(actual.get(0).getTransactionDateTime()).isAfterOrEqualTo(actual.get(1).getTransactionDateTime());
		assertThat(actual.get(1).getTransactionDateTime()).isAfterOrEqualTo(actual.get(2).getTransactionDateTime());
	}

	@DisplayName("마지막 아이디 없이 제한된 개수만큼 포인트 목록을 조회한다.")
	@Test
	void find_all_point_with_size() {
		List<Point> actual = sut.findAllByTransactorId(
			dailyQuestPoint.getTransactorId().toString(),
			null,
			2
		);

		assertThat(actual.size()).isEqualTo(2);
	}

	@DisplayName("마지막 아이디와 함께 포인트 목록을 조회한다.")
	@Test
	void find_history_quests_with_last_element_id() {
		List<Point> actual = sut.findAllByTransactorId(
			dailyQuestPoint.getTransactorId().toString(),
			weeklyQuestPoint.getId().toString(),
			2
		);

		assertThat(actual.size()).isEqualTo(1);
	}
}
