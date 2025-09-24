package com.gomo.app.quest.unit.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.core.quest.domain.model.PointReward;

@DisplayName("[Domain unit]: 포인트 보상 생성 테스트")
public class PointRewardTest {

	@DisplayName("포인트 보상을 생성한다.")
	@Test
	void create_point_reward() {
		PointReward pointReward = PointReward.of(10);

		assertThat(pointReward.getAmount()).isEqualTo(10);
	}
}
