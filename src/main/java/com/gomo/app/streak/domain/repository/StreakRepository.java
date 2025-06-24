package com.gomo.app.streak.domain.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gomo.app.streak.domain.model.AchieverId;
import com.gomo.app.streak.domain.model.Streak;
import com.gomo.app.streak.domain.model.StreakId;
import com.gomo.app.streak.domain.model.StreakType;

public interface StreakRepository extends JpaRepository<Streak, StreakId> {

	Optional<Streak> findByAchieverIdAndStreakTypeAndFilledDate(AchieverId achieverId, StreakType type, LocalDate filledDate);

	List<Streak> findByAchieverIdAndStreakTypeAndFilledDateBetween(AchieverId achieverId, StreakType type, LocalDate startDate, LocalDate endDate);

	List<Streak> findByAchieverIdAndFilledDate(AchieverId achieverId, LocalDate filledDate);
}
