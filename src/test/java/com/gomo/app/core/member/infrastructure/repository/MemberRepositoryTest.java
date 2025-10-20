package com.gomo.app.core.member.infrastructure.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.gomo.app.core.member.domain.model.ActivateStatus;
import com.gomo.app.core.member.domain.model.Email;
import com.gomo.app.core.member.domain.model.Handle;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.repository.MemberRepository;
import com.gomo.app.core.member.fixture.MemberFixture;
import com.gomo.app.test.IntegrationTest;

@DisplayName("[Domain Integration]: 회원 DB 접근 테스트")
@IntegrationTest
public class MemberRepositoryTest {

	@Autowired
	private MemberRepository sut;

	@BeforeEach
	void setUp() {
		Member member = MemberFixture.create();
		member.updateLastLoginDateTime(LocalDate.now().atStartOfDay());
		sut.save(member);
	}

	@AfterEach
	void tearDown() {
		sut.deleteAllInBatch();
	}

	@DisplayName("활성화되어 있고, 어제까지 로그인한 사용자 목록을 조회한다.")
	@Test
	void find_active_and_login_member() {
		Page<Member> members = sut.findByActivateStatusAndLastLoginDateTimeGreaterThanEqual(
			ActivateStatus.ACTIVE,
			LocalDate.now().minusDays(1).atStartOfDay(),
			PageRequest.of(0, 10)
		);

		assertThat(members).hasSize(1);
	}

	@DisplayName("이메일을 이용하여 유저의 정보를 확인한다.")
	@Test
	void find_member_info_with_email() {
		Optional<Member> actual = sut.findByEmail(Email.of("test@naver.com"));
		assertThat(actual.isPresent()).isTrue();
	}

	@DisplayName("핸들을 이용하여 유저의 정보를 확인한다.")
	@Test
	void find_member_info_with_handle() {
		Optional<Member> actual = sut.findByHandle(Handle.of("@gomo"));
		assertThat(actual.isPresent()).isTrue();
	}
}
