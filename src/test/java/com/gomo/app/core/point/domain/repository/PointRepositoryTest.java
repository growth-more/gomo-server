package com.gomo.app.core.point.domain.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.core.point.domain.model.Point;
import com.gomo.app.core.point.fixture.PointFixture;
import com.gomo.app.test.IntegrationTest;

@DisplayName("[Domain integration]: 포인트 DB 접근 테스트")
@IntegrationTest
public class PointRepositoryTest {

	@Autowired
	private PointRepository sut;

	@Autowired
	private PointRepository pointRepository;

	@AfterEach
	void tearDown() {
		pointRepository.deleteAllInBatch();
	}

	@DisplayName("마지막 아이디 없이 포인트 목록을 조회한다.")
	@Test
	void find_all_point() {
		UUID transactorId = UUIDGenerator.generate();
		Point point1 = PointFixture.create(UUIDGenerator.generate(), transactorId);
		Point point2 = PointFixture.create(UUIDGenerator.generate(), transactorId);
		pointRepository.saveAll(List.of(point1, point2));

		List<Point> actual = sut.findAllByTransactorId(
			String.valueOf(transactorId),
			null,
			null,
			10
		);

		assertThat(actual.size()).isEqualTo(2);
	}

	@DisplayName("포인트 목록은 거래일자 기준으로 내림차순 조회한다.")
	@Test
	void find_all_point_transaction_date_desc() {
		UUID transactorId = UUIDGenerator.generate();
		Point point1 = PointFixture.create(UUIDGenerator.generate(), transactorId);
		Point point2 = PointFixture.create(UUIDGenerator.generate(), transactorId);
		Point point3 = PointFixture.create(UUIDGenerator.generate(), transactorId);
		pointRepository.saveAll(List.of(point1, point2, point3));

		List<Point> actual = sut.findAllByTransactorId(
			String.valueOf(transactorId),
			null,
			null,
			10
		);

		assertThat(actual.get(0).getTransactionDateTime()).isAfterOrEqualTo(actual.get(1).getTransactionDateTime());
		assertThat(actual.get(1).getTransactionDateTime()).isAfterOrEqualTo(actual.get(2).getTransactionDateTime());
	}

	@DisplayName("마지막 아이디 없이 제한된 개수만큼 포인트 목록을 조회한다.")
	@Test
	void find_all_point_with_size() {
		UUID transactorId = UUIDGenerator.generate();
		Point point1 = PointFixture.create(UUIDGenerator.generate(), transactorId);
		Point point2 = PointFixture.create(UUIDGenerator.generate(), transactorId);
		Point point3 = PointFixture.create(UUIDGenerator.generate(), transactorId);
		pointRepository.saveAll(List.of(point1, point2, point3));

		List<Point> actual = sut.findAllByTransactorId(
			String.valueOf(transactorId),
			null,
			null,
			2
		);

		assertThat(actual.size()).isEqualTo(2);
	}

	@DisplayName("마지막 아이디와 함께 포인트 목록을 조회한다.")
	@Test
	void find_history_quests_with_last_element_id() {
		UUID transactorId = UUIDGenerator.generate();
		Point point1 = PointFixture.create(UUIDGenerator.generate(), transactorId);
		Point point2 = PointFixture.create(UUIDGenerator.generate(), transactorId);
		Point point3 = PointFixture.create(UUIDGenerator.generate(), transactorId);
		Point point4 = PointFixture.create(UUIDGenerator.generate(), transactorId);
		pointRepository.saveAll(List.of(point1, point2, point3, point4));

		List<Point> actual = sut.findAllByTransactorId(
			String.valueOf(transactorId),
			point3.getTransactionDateTime(),
			String.valueOf(point3.getId()),
			5
		);

		assertThat(actual.size()).isEqualTo(2);
	}

	@DisplayName("특정 사용자의 포인트 내역을 삭제한다.")
	@Transactional
	@Test
	void delete_all_points() {
		UUID transactorId = UUIDGenerator.generate();
		Point point1 = PointFixture.create(UUIDGenerator.generate(), transactorId);
		Point point2 = PointFixture.create(UUIDGenerator.generate(), transactorId);
		Point point3 = PointFixture.create(UUIDGenerator.generate(), transactorId);
		pointRepository.saveAll(List.of(point1, point2, point3));

		sut.deleteAllByTransactorId(transactorId);

		assertThat(sut.findAllByTransactorId(String.valueOf(transactorId), null, null, 100).size()).isZero();
	}
}
