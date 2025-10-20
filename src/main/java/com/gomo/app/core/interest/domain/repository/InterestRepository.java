package com.gomo.app.core.interest.domain.repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gomo.app.core.interest.domain.model.Interest;

public interface InterestRepository extends JpaRepository<Interest, UUID> {

	List<Interest> findAllByRegistrantId(UUID registrantId);

	List<Interest> findAllByRegistrantIdIn(Set<UUID> registrantIds);

	List<Interest> findAllByIdIsIn(Set<UUID> interestIds);

	// TODO <jhl221123> to <nurdy>: 값이 null이 아닌 데이터만 조회하기 때문에 메서드 이름 수정이 필요해 보입니다.
	//  이미지 삭제 스케줄 기능에만 사용된다면 해당 메서드 자체를 제거해주세요.
	//  ※ @Query 어노테이션을 활용해 직접 작성한 메서드는 테스트를 작성해야 합니다.
	@Query("SELECT i.logo FROM Interest i WHERE i.logo IS NOT NULL")
	List<String> findAllLogoUrl();

	@Modifying
	@Query("DELETE FROM Interest i WHERE i.registrantId = :registrantId")
	void deleteAllByRegistrantId(UUID registrantId);

	long countAllByRegistrantId(UUID registrantId);
}
