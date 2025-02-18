package com.gomo.app.member.integration;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.member.domain.model.ActivateStatus;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.repository.MemberRepository;

@DisplayName("[Domain integration]: 회원 DB 접근 테스트")
public class MemberRepositoryTest extends IntegrationTestBase {

	@Autowired
	MemberRepository sut;

	@DisplayName("활성화되어 있고, 어제까지 로그인한 사용자 목록을 조회한다.")
	@Test
	void find_active_and_login_member() {
		List<Member> members = sut.findByActivateStatusAndLastLoginDateTimeGreaterThanEqual(
			ActivateStatus.ACTIVE,
			LocalDate.now().minusDays(1).atStartOfDay()
		);

		assertThat(members.size()).isEqualTo(2);
	}
}
