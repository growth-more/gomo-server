package com.gomo.app.interest.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.RegistrantId;

public interface InterestRepository extends JpaRepository<Interest, InterestId> {

	List<Interest> findAllByRegistrantId(RegistrantId registrantId);
}
