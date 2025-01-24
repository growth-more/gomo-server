package com.gomo.app.interest.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gomo.app.interest.domain.model.MajorInterest;
import com.gomo.app.interest.domain.model.MajorInterestId;

public interface MajorInterestRepository extends JpaRepository<MajorInterest, MajorInterestId> {

}
