package com.gomo.app.interest.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gomo.app.interest.domain.model.InterestRelation;
import com.gomo.app.interest.domain.model.InterestRelationId;
import com.gomo.app.interest.domain.model.RegistrantId;

public interface InterestRelationRepository extends JpaRepository<InterestRelation, InterestRelationId> {

	List<InterestRelation> findAllByRegistrantId(RegistrantId registrantId);
}
