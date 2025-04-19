package com.gomo.app.interest.domain.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.RegistrantId;

public interface InterestRepository extends JpaRepository<Interest, InterestId> {

	List<Interest> findAllByRegistrantId(RegistrantId registrantId);

	List<Interest> findAllByIdIsIn(Set<InterestId> interestIds);

	@Query("SELECT i.logo FROM Interest i WHERE i.logo IS NOT NULL")
	List<String> findAllLogoUrl();
}
