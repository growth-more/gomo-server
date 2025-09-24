package com.gomo.app.core.interest.domain.policy;

// TODO <jhl221123>: int[] 배열 대신 일급 객체로 관리하도록 수정이 필요합니다.
public interface ScoreThresholdPolicyProvider {

	int[] getScoreThresholdPerLevel();

	int[] getTotalScoreForLevel();
}
