package com.gomo.app.streak.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gomo.app.streak.domain.model.Achiever;
import com.gomo.app.streak.domain.model.AchieverId;

public interface AchieverRepository extends JpaRepository<Achiever, AchieverId> {
}
