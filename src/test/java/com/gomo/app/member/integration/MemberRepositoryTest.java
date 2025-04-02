package com.gomo.app.member.integration;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.member.domain.model.ActivateStatus;
import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.Handle;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.repository.MemberRepository;

@DisplayName("[Domain integration]: 회원 DB 접근 테스트")
public class MemberRepositoryTest extends IntegrationTestBase {

	@Autowired
	MemberRepository sut;

	private static final String EMAIL ="gomotest@naver.com";
	private static final String HANDLE = "@GOMOTEST2";

	@DisplayName("활성화되어 있고, 어제까지 로그인한 사용자 목록을 조회한다.")
	@Test
	void find_active_and_login_member() {
		List<Member> members = sut.findByActivateStatusAndLastLoginDateTimeGreaterThanEqual(
			ActivateStatus.ACTIVE,
			LocalDate.now().minusDays(1).atStartOfDay()
		);

		assertThat(members.size()).isEqualTo(2);
	}

	@DisplayName("이메일이 존재하는지 확인한다.")
	@Test
	void check_exist_email(){
		boolean actual = sut.existsByEmail(Email.of(EMAIL));
		assertThat(actual).isTrue();
	}

	@DisplayName("핸들이 이미 사용중인지 확인한다.")
	@Test
	void check_exist_handle(){
		boolean actual = sut.existsByHandle(Handle.of(HANDLE));
		assertThat(actual).isTrue();
	}

	@DisplayName("이메일을 이용하여 유저의 정보를 확인한다.")
	@Test
	void check_member_info_using_email(){
		Optional<Member> actual = sut.findByEmail(Email.of(EMAIL));
		assertThat(actual.isPresent()).isTrue();
	}

	@DisplayName("사용중인 프로필이미지 URL 을 확인한다.")
	@Test
	void check_all_profile_image_url(){
		List<String> actual = sut.findAllByProfileImageUrl();
		assertThat(actual).isNotNull();
	}

}
