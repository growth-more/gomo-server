package com.gomo.app.point.integration;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.core.point.domain.model.Point;
import com.gomo.app.core.point.domain.model.SourceType;
import com.gomo.app.core.point.domain.model.TransactionType;
import com.gomo.app.core.point.domain.model.TransactorId;
import com.gomo.app.core.point.domain.repository.PointRepository;
import com.gomo.app.core.point.domain.repository.PointWalletRepository;
import com.gomo.app.core.point.domain.service.PointService;
import com.gomo.app.point.fixture.PointWalletFixture;

@DisplayName("[Domain integration]: 포인트 DB 접근 테스트")
public class PointRepositoryTest extends IntegrationTestBase {

	@Autowired
	PointRepository sut;

	@Autowired
	private PointService pointService;
	private UUID transactorId;
	private UUID offsetId;

	@Autowired
	private PointRepository pointRepository;

	@Autowired
	private PointWalletRepository pointWalletRepository;

	@BeforeEach
	public void setUp() {
		transactorId = UUID.randomUUID();
		pointWalletRepository.save(PointWalletFixture.point(transactorId, 1660));
		pointService.create(TransactorId.of(transactorId), SourceType.QUEST, TransactionType.GAIN, 10);
		offsetId = pointService.create(TransactorId.of(transactorId), SourceType.QUEST, TransactionType.GAIN, 150);
		pointService.create(TransactorId.of(transactorId), SourceType.QUEST, TransactionType.GAIN, 1500);
	}

	@AfterEach
	void tearDown() {
		pointRepository.deleteAllInBatch();
		pointWalletRepository.deleteAllInBatch();
	}

	@DisplayName("마지막 아이디 없이 포인트 목록을 조회한다.")
	@Test
	void find_all_point() {
		List<Point> actual = sut.findAllByTransactorId(
			String.valueOf(transactorId),
			null,
			10
		);

		assertThat(actual.size()).isEqualTo(3);
	}

	@DisplayName("포인트 목록은 거래일자 기준으로 내림차순 조회한다.")
	@Test
	void find_all_point_transaction_date_desc() {
		List<Point> actual = sut.findAllByTransactorId(
			String.valueOf(transactorId),
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
			String.valueOf(transactorId),
			null,
			2
		);

		assertThat(actual.size()).isEqualTo(2);
	}

	@DisplayName("마지막 아이디와 함께 포인트 목록을 조회한다.")
	@Test
	void find_history_quests_with_last_element_id() {
		List<Point> actual = sut.findAllByTransactorId(
			String.valueOf(transactorId),
			String.valueOf(offsetId),
			2
		);

		assertThat(actual.size()).isEqualTo(1);
	}

	@DisplayName("특정 사용자의 포인트 내역을 삭제한다.")
	@Transactional
	@Test
	void delete_all_points() {

		sut.deleteAllByTransactorId(TransactorId.of(transactorId));

		assertThat(sut.findAllByTransactorId(String.valueOf(transactorId), null, 100).size())
			.isZero();
	}
}
