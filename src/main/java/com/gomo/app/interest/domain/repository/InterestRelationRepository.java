package com.gomo.app.interest.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gomo.app.interest.domain.model.InterestRelation;
import com.gomo.app.interest.domain.model.InterestRelationId;
import com.gomo.app.interest.domain.model.RegistrantId;

public interface InterestRelationRepository extends JpaRepository<InterestRelation, InterestRelationId> {

	List<InterestRelation> findAllByRegistrantId(RegistrantId registrantId);

	@Query("select COUNT(ir) > 0 from InterestRelation ir "
		+ "where (ir.parentInterestId.id = :firstInterestId "
		+ "and ir.childInterestId.id = :secondInterestId) "
		+ "or (ir.parentInterestId.id = :secondInterestId "
		+ "and ir.childInterestId.id = :firstInterestId)")
	boolean existsRelationFor(UUID firstInterestId, UUID secondInterestId);

	@Query("select ir from InterestRelation ir "
		+ "where ir.parentInterestId.id = :interestId "
		+ "or ir.childInterestId.id = :interestId")
	List<InterestRelation> findByInterestId(UUID interestId);
}
