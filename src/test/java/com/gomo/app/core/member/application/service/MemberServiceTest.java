package com.gomo.app.core.member.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.application.port.dto.MemberDto;
import com.gomo.app.core.member.application.port.out.MemberLogoutProcessor;
import com.gomo.app.core.member.application.port.out.PointBalanceReader;
import com.gomo.app.core.member.domain.exception.MemberNotFoundException;
import com.gomo.app.core.member.domain.exception.code.MemberErrorCode;
import com.gomo.app.core.member.domain.model.ActivateStatus;
import com.gomo.app.core.member.domain.model.Email;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.repository.MemberRepository;
import com.gomo.app.core.member.fixture.MemberFixture;

@DisplayName("[Application unit]: 멤버 조회 테스트")
@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

	@InjectMocks
	private MemberService sut;

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private PointBalanceReader pointBalanceReader;

	@Mock
	private MemberLogoutProcessor memberLogoutProcessor;

	private static final int BALANCE = 5000;

	@DisplayName("멤버 조회에 성공한다")
	@Test
	void read_member_successfully() {
		Member member = MemberFixture.create();
		MemberDto expected = MemberDto.from(member, BALANCE);

		doReturn(Optional.of(member)).when(memberRepository).findById(any());
		doReturn(BALANCE).when(pointBalanceReader).read(any());

		MemberDto actual = sut.read(member.getId());

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}

	@DisplayName("회원 엔티티를 조회한다.")
	@Test
	void read_member() {
		doReturn(Optional.of(mock(Member.class))).when(memberRepository).findById(any());

		Member actual = sut.findById(UUID.randomUUID());

		assertThat(actual).isNotNull();
	}

	@DisplayName("존재하지 않는 회원 엔티티를 조회한다.")
	@Test
	void read_nonexistent_member() {
		doReturn(Optional.empty()).when(memberRepository).findById(any());

		assertThatThrownBy(() -> sut.findById(UUID.randomUUID()))
			.isInstanceOf(MemberNotFoundException.class)
			.hasMessageContaining(MemberErrorCode.NOT_FOUND.getMessage());
	}

	@DisplayName("이메일로 회원 엔티티를 조회한다.")
	@Test
	void read_member_by_mail() {
		doReturn(Optional.of(mock(Member.class))).when(memberRepository).findByEmail(any(Email.class));

		Member actual = sut.findByEmail("gomo@naver.com");

		assertThat(actual).isNotNull();
	}

	@DisplayName("이메일로 존재하지 않는 회원 엔티티를 조회한다.")
	@Test
	void read_nonexistent_member_by_email() {
		doReturn(Optional.empty()).when(memberRepository).findByEmail(any(Email.class));

		assertThatThrownBy(() -> sut.findByEmail("nonexistent@naver.com"))
			.isInstanceOf(MemberNotFoundException.class)
			.hasMessageContaining(MemberErrorCode.NOT_FOUND.getMessage());
	}

	@DisplayName("회원 정보(모토, 이름)을 수정한다.")
	@Test
	void update_member_name_and_motto() {
		Member member = MemberFixture.create();
		doReturn(Optional.of(member)).when(memberRepository).findById(member.getId());
		sut.update(member.getId(), "NEW_NAME", "NEW_MOTTO");
		assertThat(member.name()).isEqualTo("NEW_NAME");
		assertThat(member.motto()).isEqualTo("NEW_MOTTO");
	}

	@DisplayName("멤버 삭제 테스트")
	@Test
	void delete_member() {
		Member member = MemberFixture.create();
		doReturn(Optional.of(member)).when(memberRepository).findById(member.getId());
		doNothing().when(memberLogoutProcessor).logout(member.getId());

		sut.delete(member.getId());

		assertThat(member.getActivateStatus()).isEqualTo(ActivateStatus.DELETED);
	}
}
