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

	// TODO <jhl221123> to <nurdy>: 값이 null이 아닌 데이터만 조회하기 때문에 메서드 이름 수정이 필요해 보입니다.
	//  이미지 삭제 스케줄 기능에만 사용된다면 해당 메서드 자체를 제거해주세요.
	//  ※ @Query 어노테이션을 활용해 직접 작성한 메서드는 테스트를 작성해야 합니다.
	@Query("SELECT i.logo FROM Interest i WHERE i.logo IS NOT NULL")
	List<String> findAllLogoUrl();

	long countAllByRegistrantId(RegistrantId registrantId);
}
