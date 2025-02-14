package com.gomo.app.interest.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.MajorInterest;
import com.gomo.app.interest.domain.model.MajorInterestId;
import com.gomo.app.interest.domain.model.RegistrantId;

public interface MajorInterestRepository extends JpaRepository<MajorInterest, MajorInterestId> {

	Optional<MajorInterest> findByInterestId(InterestId interestId);

	@Query("select COALESCE(MAX(m.displayOrder.displayOrder), 0) from MajorInterest m " +
		"where m.registrantId = :registrantId ")
	int findMaxDisplayOrder(
		@Param("registrantId") RegistrantId registrantId
	);

	List<MajorInterest> findAllByRegistrantIdOrderByDisplayOrder(RegistrantId registrantId);

	void deleteByInterestId(InterestId interestId);
}
