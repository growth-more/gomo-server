package com.gomo.app.streak.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gomo.app.streak.domain.model.Achiever;
import com.gomo.app.streak.domain.model.AchieverId;

public interface AchieverRepository extends JpaRepository<Achiever, AchieverId> {

	@Modifying
	@Query("DELETE FROM Achiever a WHERE a.id =: achieverId")
	void deleteByAchieverId(AchieverId achieverId);
}
