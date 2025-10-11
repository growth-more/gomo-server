package com.gomo.app.core.streak.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gomo.app.core.streak.domain.model.Achiever;

public interface AchieverRepository extends JpaRepository<Achiever, UUID> {

	@Modifying
	@Query("DELETE FROM Achiever a WHERE a.id =: achieverId")
	void deleteByAchieverId(UUID achieverId);
}
