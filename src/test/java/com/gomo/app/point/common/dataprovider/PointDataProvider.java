package com.gomo.app.point.common.dataprovider;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gomo.app.point.domain.model.Point;
import com.gomo.app.point.domain.model.PointId;
import com.gomo.app.point.domain.repository.PointRepository;

import jakarta.annotation.PostConstruct;

/**
 * 실제 데이터베이스에 존재하는 포인트 데이터를 제공한다.
 * @ 총 세 가지 데이터가 존재한다.
 * @ 1. daily quest gain(2025-01-20)
 * @ 2. weekly quest gain(2025-01-21)
 * @ 3. monthly quest gain(2025-01-22)
 */
@Component
public class PointDataProvider {

	private static final String DAILY_QUEST_GAIN_POINT_ID = "7dbbdedb-d734-11ef-9c48-394e79c4a67c";
	private static final String WEEKLY_QUEST_GAIN_POINT_ID = "c9f68773-d735-11ef-9d10-57f4db82cd9c";
	private static final String MONTHLY_QUEST_GAIN_POINT_ID = "ead8cd48-d735-11ef-b568-8745309ead01";
	private Point dailyQuest;
	private Point weeklyQuest;
	private Point monthlyQuest;

	@Autowired
	private PointRepository pointRepository;

	@PostConstruct
	public void initialize() {
		dailyQuest = pointRepository.findById(PointId.of(UUID.fromString(DAILY_QUEST_GAIN_POINT_ID)))
			.orElseThrow(() -> new IllegalStateException("PointDataProvider 초기화 실패: DAILY_QUEST_GAIN_POINT_ID에 해당하는 Point가 없습니다."));

		weeklyQuest = pointRepository.findById(PointId.of(UUID.fromString(WEEKLY_QUEST_GAIN_POINT_ID)))
			.orElseThrow(() -> new IllegalStateException("PointDataProvider 초기화 실패: WEEKLY_QUEST_GAIN_POINT_ID에 해당하는 Point가 없습니다."));

		monthlyQuest = pointRepository.findById(PointId.of(UUID.fromString(MONTHLY_QUEST_GAIN_POINT_ID)))
			.orElseThrow(() -> new IllegalStateException("PointDataProvider 초기화 실패: MONTHLY_QUEST_GAIN_POINT_ID에 해당하는 Point가 없습니다."));
	}

	public Point dailyQuest() {
		return dailyQuest;
	}

	public Point weeklyQuest() {
		return weeklyQuest;
	}

	public Point monthlyQuest() {
		return monthlyQuest;
	}
}
