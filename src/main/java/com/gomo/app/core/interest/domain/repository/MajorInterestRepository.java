package com.gomo.app.core.interest.domain.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gomo.app.core.interest.domain.model.MajorInterest;

public interface MajorInterestRepository extends JpaRepository<MajorInterest, UUID> {

	Optional<MajorInterest> findByInterestId(UUID interestId);

	List<MajorInterest> findByInterestIdIn(Collection<UUID> interestIds);

	@Query("select COALESCE(MAX(m.displayOrder.displayOrder), 0) from MajorInterest m " +
		"where m.registrantId = :registrantId ")
	int findMaxDisplayOrder(@Param("registrantId") UUID registrantId);

	List<MajorInterest> findAllByRegistrantIdAndInterestIdIn(UUID registrantId, Collection<UUID> interestIds);

	List<MajorInterest> findAllByRegistrantIdOrderByDisplayOrder(UUID registrantId);

	void deleteByInterestId(UUID interestId);

	@Modifying
	@Query("DELETE FROM MajorInterest mi WHERE mi.registrantId = :registrantId")
	void deleteAllByRegistrantId(UUID registrantId);
}
