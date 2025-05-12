package com.gomo.app.member.integration;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.model.ActivateStatus;
import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.Handle;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.repository.MemberRepository;

@DisplayName("[Domain Integration]: 회원 DB 접근 테스트")
public class MemberRepositoryTest extends IntegrationTestBase {

	@Autowired
	MemberRepository sut;

	@BeforeEach
	void setUp() {
		Member member = MemberFixture.member();
		member.updateLastLoginDateTime(LocalDate.now().atStartOfDay());
		sut.save(member);
	}

	@AfterEach
	void tearDown() {
		sut.deleteAllInBatch();
	}

	private static final String EMAIL = "test@naver.com";
	private static final String HANDLE = "@gomo";

	@DisplayName("활성화되어 있고, 어제까지 로그인한 사용자 목록을 조회한다.")
	@Test
	void find_active_and_login_member() {
		List<Member> members = sut.findByActivateStatusAndLastLoginDateTimeGreaterThanEqual(
			ActivateStatus.ACTIVE,
			LocalDate.now().minusDays(1).atStartOfDay()
		);

		assertThat(members).hasSize(1);
	}

	@DisplayName("이메일을 이용하여 유저의 정보를 확인한다.")
	@Test
	void find_member_info_with_email() {
		Optional<Member> actual = sut.findByEmail(Email.of(EMAIL));
		assertThat(actual.isPresent()).isTrue();
	}

	@DisplayName("핸들을 이용하여 유저의 정보를 확인한다.")
	@Test
	void find_member_info_with_handle() {
		Optional<Member> actual = sut.findByHandle(Handle.of(HANDLE));
		assertThat(actual.isPresent()).isTrue();
	}
}
