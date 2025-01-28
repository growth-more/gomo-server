package com.gomo.app.interest.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.MajorInterest;
import com.gomo.app.interest.domain.model.MajorInterestId;
import com.gomo.app.interest.domain.model.RegistrantId;

public interface MajorInterestRepository extends JpaRepository<MajorInterest, MajorInterestId> {

	Optional<MajorInterest> findByInterestId(InterestId interestId);

	long countAllByRegistrantId(RegistrantId registrantId);

	List<MajorInterest> findAllByRegistrantIdOrderByDisplayOrder(RegistrantId registrantId);
}
