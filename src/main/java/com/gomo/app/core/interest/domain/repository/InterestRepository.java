package com.gomo.app.core.interest.domain.repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gomo.app.core.interest.domain.model.Interest;

public interface InterestRepository extends JpaRepository<Interest, UUID> {

	List<Interest> findAllByRegistrantId(UUID registrantId);

	List<Interest> findAllByRegistrantIdIn(Set<UUID> registrantIds);

	@Modifying
	@Query("DELETE FROM Interest i WHERE i.registrantId = :registrantId")
	void deleteAllByRegistrantId(UUID registrantId);

	long countAllByRegistrantId(UUID registrantId);
}
