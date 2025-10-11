package com.gomo.app.core.interest.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gomo.app.core.interest.domain.model.InterestRelation;

public interface InterestRelationRepository extends JpaRepository<InterestRelation, UUID> {

	List<InterestRelation> findAllByRegistrantId(UUID registrantId);

	@Query("select COUNT(ir) > 0 from InterestRelation ir "
		+ "where (ir.parentInterestId = :firstInterestId "
		+ "and ir.childInterestId = :secondInterestId) "
		+ "or (ir.parentInterestId = :secondInterestId "
		+ "and ir.childInterestId = :firstInterestId)")
	boolean existsRelationFor(UUID firstInterestId, UUID secondInterestId);

	@Query("select ir from InterestRelation ir "
		+ "where ir.parentInterestId = :interestId "
		+ "or ir.childInterestId = :interestId")
	List<InterestRelation> findAllByInterestId(UUID interestId);

	@Modifying
	@Query("DELETE FROM InterestRelation ir WHERE ir.registrantId = :registrantId")
	void deleteAllByRegistrantId(UUID registrantId);
}
