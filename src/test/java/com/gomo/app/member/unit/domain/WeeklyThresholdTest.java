package com.gomo.app.member.unit.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.common.exception.PolicyViolationException;
import com.gomo.app.member.domain.model.WeeklyThreshold;

@DisplayName("[Domain unit]: 주간 퀘스트 제한 테스트")
public class WeeklyThresholdTest {

    private static final int MINIMUM_THRESHOLD = 0;
    private static final int MAXIMUM_THRESHOLD = 15;
    private static final int DEFAULT_THRESHOLD = 3;

    @DisplayName("주간 퀘스트 제한을 생성한다.")
    @Test
    void create_weekly_threshold(){
        WeeklyThreshold weeklyThreshold = WeeklyThreshold.of(10);

        assertThat(weeklyThreshold.getThreshold()).isEqualTo(10);
    }

    @DisplayName("주간 크키의 일일 퀘스트 제한을 생성한다.")
    @Test
    void create_weekly_threshold_with_default_threshold(){
        WeeklyThreshold weeklyThreshold = WeeklyThreshold.createDefault();

        assertThat(weeklyThreshold.getThreshold()).isEqualTo(DEFAULT_THRESHOLD);
    }

    @DisplayName("주간 크기의 일일 퀘스트 제한을 생성한다.")
    @Test
    void create_weekly_threshold_with_minimum_threshold(){
        WeeklyThreshold weeklyThreshold = WeeklyThreshold.of(MINIMUM_THRESHOLD);

        assertThat(weeklyThreshold.getThreshold()).isEqualTo(MINIMUM_THRESHOLD);
    }

    @DisplayName("최솟값 보다 작은 주간 퀘스트 제한은 생성할 수 없다.")
    @Test
    void create_weekly_threshold_under_minimum_threshold(){
        assertThatThrownBy(() -> WeeklyThreshold.of(MINIMUM_THRESHOLD - 1))
            .isInstanceOf(PolicyViolationException.class)
            .hasMessageContaining("Invalid threshold range");
    }

    @DisplayName("최대 크기의 주간 퀘스트 제한을 생성한다.")
    @Test
    void create_weekly_threshold_with_maximum_threshold(){
        WeeklyThreshold weeklyThreshold = WeeklyThreshold.of(MAXIMUM_THRESHOLD);

        assertThat(weeklyThreshold.getThreshold()).isEqualTo(MAXIMUM_THRESHOLD);
    }

    @DisplayName("최대값 보다 큰 주간 퀘스트 제한은 생성할 수 없다.")
    @Test
    void create_weekly_threshold_over_maximum_threshold(){
        assertThatThrownBy(() -> WeeklyThreshold.of(MAXIMUM_THRESHOLD + 1))
                .isInstanceOf(PolicyViolationException.class)
                .hasMessageContaining("Invalid threshold range");
    }

    @DisplayName("주간 퀘스트 제한을 수정한다.")
    @Test
    void update_weekly_threshold(){
        WeeklyThreshold weeklyThreshold = WeeklyThreshold.of(10);
        WeeklyThreshold updatedWeeklyThreshold = weeklyThreshold.update(11);

        assertThat(updatedWeeklyThreshold.getThreshold()).isEqualTo(11);
    }

    @DisplayName("최소 크기로 주간 퀘스트 제한을 수정한다.")
    @Test
    void update_weekly_threshold_with_minimum_threshold(){
        WeeklyThreshold weeklyThreshold = WeeklyThreshold.of(10);
        WeeklyThreshold updatedWeeklyThreshold = weeklyThreshold.update(MINIMUM_THRESHOLD);

        assertThat(updatedWeeklyThreshold.getThreshold()).isEqualTo(MINIMUM_THRESHOLD);
    }

    @DisplayName("주간 퀘스트 제한은 최솟값 보다 작게 수정할 수 없다.")
    @Test
    void update_weekly_threshold_under_minimum_threshold(){
        WeeklyThreshold weeklyThreshold = WeeklyThreshold.of(10);

        assertThatThrownBy(() -> weeklyThreshold.update(MINIMUM_THRESHOLD - 1))
            .isInstanceOf(PolicyViolationException.class)
            .hasMessageContaining("Invalid threshold range");
    }

    @DisplayName("최대 크기로 주간 퀘스트 제한을 수정한다.")
    @Test
    void update_weekly_threshold_with_maximum_threshold(){
        WeeklyThreshold weeklyThreshold = WeeklyThreshold.of(10);
        WeeklyThreshold updatedWeeklyThreshold = weeklyThreshold.update(MAXIMUM_THRESHOLD);

        assertThat(updatedWeeklyThreshold.getThreshold()).isEqualTo(MAXIMUM_THRESHOLD);
    }

    @DisplayName("주간 퀘스트 제한은 최대값 보다 크게 수정할 수 없다.")
    @Test
    void update_weekly_threshold_over_maximum_threshold(){
        WeeklyThreshold weeklyThreshold = WeeklyThreshold.of(10);

        assertThatThrownBy(() -> weeklyThreshold.update(MAXIMUM_THRESHOLD + 1))
            .isInstanceOf(PolicyViolationException.class)
            .hasMessageContaining("Invalid threshold range");
    }
}
