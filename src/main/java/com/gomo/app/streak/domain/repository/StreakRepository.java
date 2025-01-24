package com.gomo.app.streak.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gomo.app.streak.domain.model.Streak;
import com.gomo.app.streak.domain.model.StreakId;

public interface StreakRepository extends JpaRepository<Streak, StreakId> {
}
