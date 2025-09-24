package com.gomo.app.core.interest.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gomo.app.core.interest.domain.model.InterestRelation;
import com.gomo.app.core.interest.domain.model.InterestRelationId;
import com.gomo.app.core.interest.domain.model.RegistrantId;

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
	List<InterestRelation> findAllByInterestId(UUID interestId);

	@Modifying
	@Query("DELETE FROM InterestRelation ir WHERE ir.registrantId = :registrantId")
	void deleteAllByRegistrantId(RegistrantId registrantId);
}
