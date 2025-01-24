package com.gomo.app.quest.common.fixture.assign;

import org.springframework.stereotype.Component;

/**
 * 할당 퀘스트 과거 이력 픽스처
 * 실제 데이터베이스에 존재하는 테스트 레코드와 동일한 값을 수동으로 지정해서 사용한다.
 * @ TODO <jhl221123>: 추후 수정 필요
 */
@Component
public class HistoryAssignQuestFixture {

	private static final String HISTORY1_ID = "210891d5-d814-11ef-9cc5-cdb1eaaaac96";
	private static final String HISTORY2_ID = "996604d8-d814-11ef-8d8d-fdccfa1ea3b3";

	public static String history1Id() {
		return HISTORY1_ID;
	}

	public static String history2Id() {
		return HISTORY2_ID;
	}
}
