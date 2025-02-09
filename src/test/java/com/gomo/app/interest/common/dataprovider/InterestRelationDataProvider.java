package com.gomo.app.interest.common.dataprovider;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gomo.app.interest.domain.model.InterestRelation;
import com.gomo.app.interest.domain.model.InterestRelationId;
import com.gomo.app.interest.domain.repository.InterestRelationRepository;

import jakarta.annotation.PostConstruct;

/**
 * 실제 데이터베이스에 존재하는 관심사 관계 데이터를 제공한다.
 * @ backend - java : 관심사 관계가 데이터베이스에 존재한다. 관계 삭제 및 중복 테스트에서 사용한다.
 * @ backend - spring : 관심사 관계가 데이터베이스에 존재하지 않는다. 관계 생성 테스트에서 사용한다.
 */
@Component
public class InterestRelationDataProvider {

	private static final String BACKEND_TO_JAVA_ID = "80ac5a74-d7eb-11ef-a2bd-1f5e37eb89a8";

	private InterestRelation backendToJava;

	@Autowired
	private InterestRelationRepository interestRelationRepository;

	@PostConstruct
	public void initialize() {
		backendToJava = interestRelationRepository.findById(InterestRelationId.of(UUID.fromString(BACKEND_TO_JAVA_ID)))
			.orElseThrow(() -> new IllegalStateException("InterestRelationDataProvider 초기화 실패: BACKEND_TO_JAVA_ID에 해당하는 InterestRelation가 없습니다."));
	}

	public InterestRelation backendToJava() {
		return backendToJava;
	}
}
