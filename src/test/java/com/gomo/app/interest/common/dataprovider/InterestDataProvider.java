package com.gomo.app.interest.common.dataprovider;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.repository.InterestRepository;

import jakarta.annotation.PostConstruct;

/**
 * 실제 데이터베이스에 존재하는 관심사 데이터를 제공한다.
 * @ backend는 상위 관심사, java와 spring은 하위 관심사이다.
 * @ backend는 주요 관심사로 등록되어 있지 않기 때문에 주요 관심사 등록 테스트에서 사용한다.
 * @ java와 spring은 주요 관심사로 등록되어 있기 때문에 주요 관심사 등록 테스트에서 사용할 수 없다.
 */
@Component
public class InterestDataProvider {

	private static final String BACKEND_ID = "3bd1b3f7-d7c6-11ef-abb8-a7e09b2a499c";
	private static final String JAVA_ID = "f8c51811-d7c5-11ef-82dc-4322ccc3e338";
	private static final String SPRING_ID = "90a387a7-d7c5-11ef-b4d7-079c7dc41274";
	private Interest backend;
	private Interest java;
	private Interest spring;

	@Autowired
	private InterestRepository interestRepository;

	@PostConstruct
	public void initialize() {
		backend = interestRepository.findById(InterestId.of(UUID.fromString(BACKEND_ID)))
			.orElseThrow(() -> new IllegalStateException("InterestDataProvider 초기화 실패: BACKEND_ID에 해당하는 Interest가 없습니다."));

		java = interestRepository.findById(InterestId.of(UUID.fromString(JAVA_ID)))
			.orElseThrow(() -> new IllegalStateException("InterestDataProvider 초기화 실패: JAVA_ID에 해당하는 Interest가 없습니다."));

		spring = interestRepository.findById(InterestId.of(UUID.fromString(SPRING_ID)))
			.orElseThrow(() -> new IllegalStateException("InterestDataProvider 초기화 실패: SPRING_ID에 해당하는 Interest가 없습니다."));
	}

	public Interest backend() {
		return backend;
	}

	public Interest java() {
		return java;
	}

	public Interest spring() {
		return spring;
	}
}
