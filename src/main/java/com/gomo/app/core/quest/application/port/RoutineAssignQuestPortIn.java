package com.gomo.app.core.quest.application.port;

public interface RoutineAssignQuestPortIn {

	// TODO [2025-10-07] jhl221123 : 실제 배치 작업 시, 중단 부분을 기록하고 재시작하기 위해 파라미터 및 기능 수정이 팔요합니다.

	/**
	 * Creates assign quests for all active members for a specific routine cycle (e.g., daily, weekly).
	 * <p>
	 * It creates both repeat quests and quest pool.
	 * This is intended to be called by a scheduled job.
	 *
	 * @param questType The routine cycle for which to create quests (e.g., "DAILY", "WEEKLY").
	 */
	void createForActiveMembers(String questType);
}
