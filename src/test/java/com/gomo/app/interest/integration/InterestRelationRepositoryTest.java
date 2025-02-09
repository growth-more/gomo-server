package com.gomo.app.interest.integration;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.interest.common.dataprovider.InterestRelationDataProvider;
import com.gomo.app.interest.domain.model.InterestRelation;
import com.gomo.app.interest.domain.repository.InterestRelationRepository;

@DisplayName("[Domain integration]: 관심사 관계선 중복 확인 테스트")
public class InterestRelationRepositoryTest extends IntegrationTestBase {

	@Autowired
	InterestRelationRepository sut;

	@Autowired
	private InterestRelationDataProvider interestRelationDataProvider;
	private InterestRelation backendToJava;

	@BeforeEach
	public void setUp() {
		backendToJava = interestRelationDataProvider.backendToJava();
	}

	@DisplayName("관심사 관계선이 이미 존재하는지 확인한다.")
	@Test
	void check_exists() {
		boolean parentToChild = sut.existsRelationFor(backendToJava.getParentInterestId().getId(), backendToJava.getChildInterestId().getId());
		boolean childToParent = sut.existsRelationFor(backendToJava.getChildInterestId().getId(), backendToJava.getParentInterestId().getId());

		assertThat(parentToChild).isTrue();
		assertThat(childToParent).isTrue();
	}
}
