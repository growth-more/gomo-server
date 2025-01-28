package com.gomo.app.interest.common.dataprovider;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gomo.app.interest.domain.model.MajorInterest;
import com.gomo.app.interest.domain.model.MajorInterestId;
import com.gomo.app.interest.domain.repository.MajorInterestRepository;

import jakarta.annotation.PostConstruct;

/**
 * 실제 데이터베이스에 존재하는 주요 관심사 데이터를 제공한다.
 * @ spring과 java, 두 가지 주요 관심사가 있다.
 */
@Component
public class MajorInterestDataProvider {

	private static final String JAVA_ID = "172916b7-d7f6-11ef-b7fd-0bd5a273f618";
	private static final String SPRING_ID = "bec49c34-d7f5-11ef-8497-edeb32532766";
	private MajorInterest java;
	private MajorInterest spring;

	@Autowired
	private MajorInterestRepository majorInterestRepository;

	@PostConstruct
	public void initialize() {
		java = majorInterestRepository.findById(MajorInterestId.of(UUID.fromString(JAVA_ID)))
			.orElseThrow(() -> new IllegalStateException("MajorInterestDataProvider 초기화 실패: JAVA_ID에 해당하는 MajorInterest가 없습니다."));

		spring = majorInterestRepository.findById(MajorInterestId.of(UUID.fromString(SPRING_ID)))
			.orElseThrow(() -> new IllegalStateException("MajorInterestDataProvider 초기화 실패: SPRING_ID에 해당하는 MajorInterest가 없습니다."));
	}

	public MajorInterest java() {
		return java;
	}

	public MajorInterest spring() {
		return spring;
	}
}
