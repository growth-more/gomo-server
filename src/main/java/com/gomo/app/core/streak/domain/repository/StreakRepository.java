package com.gomo.app.core.streak.domain.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gomo.app.core.streak.domain.model.Streak;
import com.gomo.app.core.streak.domain.model.StreakType;

public interface StreakRepository extends JpaRepository<Streak, UUID> {

	Optional<Streak> findByAchieverIdAndStreakTypeAndFilledDate(UUID achieverId, StreakType type, LocalDate filledDate);

	List<Streak> findByAchieverIdAndStreakTypeAndFilledDateBetween(UUID achieverId, StreakType type, LocalDate startDate, LocalDate endDate);

	List<Streak> findByAchieverIdAndFilledDate(UUID achieverId, LocalDate filledDate);

	@Modifying
	@Query("DELETE FROM Streak s WHERE s.achieverId = :achieverId")
	void deleteAllByAchieverId(UUID achieverId);
}
