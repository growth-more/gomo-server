package com.gomo.app.member.unit.domain;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.member.domain.model.ActivateStatus;
import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.Handle;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.member.exception.ActivateStatusException;
import com.gomo.app.member.exception.EmailDuplicatedException;
import com.gomo.app.member.exception.HandleDuplicatedException;
import com.gomo.app.member.exception.MemberNotFoundException;
import com.gomo.app.member.exception.code.ActivateStatusErrorCode;
import com.gomo.app.member.exception.code.EmailErrorCode;
import com.gomo.app.member.exception.code.HandleErrorCode;
import com.gomo.app.member.exception.code.MemberErrorCode;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Domain unit]: MemberService 테스트")
public class MemberServiceTest {

	@InjectMocks
	MemberService sut;

	@Mock
	MemberRepository memberRepository;

	@DisplayName("회원 엔티티를 조회한다.")
	@Test
	void find_member() {
		doReturn(Optional.of(mock(Member.class))).when(memberRepository).findById(any(MemberId.class));

		Member actual = sut.find(MemberId.of(UUID.randomUUID()));

		assertThat(actual).isNotNull();
	}

	@DisplayName("존재하지 않는 회원 엔티티를 조회한다.")
	@Test
	void find_nonexistent_member() {
		doReturn(Optional.empty()).when(memberRepository).findById(any(MemberId.class));

		assertThatThrownBy(() -> sut.find(MemberId.of(UUID.randomUUID())))
			.isInstanceOf(MemberNotFoundException.class)
			.hasMessageContaining(MemberErrorCode.NOT_FOUND.getMessage());
	}

	@DisplayName("이메일로 회원 엔티티를 조회한다.")
	@Test
	void find_member_by_mail() {
		doReturn(Optional.of(mock(Member.class))).when(memberRepository).findByEmail(any(Email.class));

		Member actual = sut.findByEmail(Email.of("gomo@naver.com"));

		assertThat(actual).isNotNull();
	}

	@DisplayName("이메일로 존재하지 않는 회원 엔티티를 조회한다.")
	@Test
	void find_nonexistent_member_by_email() {
		doReturn(Optional.empty()).when(memberRepository).findByEmail(any(Email.class));

		assertThatThrownBy(() -> sut.findByEmail(Email.of("nonexistent@naver.com")))
			.isInstanceOf(MemberNotFoundException.class)
			.hasMessageContaining(MemberErrorCode.NOT_FOUND.getMessage());
	}

	@DisplayName("이메일이 중복되지 않는다.")
	@Test
	void check_non_duplicated_email() {
		doReturn(Optional.empty()).when(memberRepository).findByEmail(any(Email.class));

		assertThatCode(() -> sut.checkEmailDuplicated(Email.of("nonexistent@naver.com")))
			.doesNotThrowAnyException();
	}

	@DisplayName("이메일이 중복된다.")
	@Test
	void check_duplicated_email() {
		doReturn(Optional.of(mock(Member.class))).when(memberRepository).findByEmail(any(Email.class));

		assertThatThrownBy(() -> sut.checkEmailDuplicated(Email.of("gomo@naver.com")))
			.isInstanceOf(EmailDuplicatedException.class)
			.hasMessageContaining(EmailErrorCode.DUPLICATED.getMessage());
	}

	@DisplayName("핸들이 중복되지 않는다.")
	@Test
	void check_non_duplicated_handle() {
		doReturn(Optional.empty()).when(memberRepository).findByHandle(any(Handle.class));

		assertThatCode(() -> sut.checkHandleDuplicated(Handle.of("@nonexistent")))
			.doesNotThrowAnyException();
	}

	@DisplayName("핸들이 중복된다.")
	@Test
	void check_duplicated_handle() {
		doReturn(Optional.of(mock(Member.class))).when(memberRepository).findByHandle(any(Handle.class));

		assertThatThrownBy(() -> sut.checkHandleDuplicated(Handle.of("@gomo")))
			.isInstanceOf(HandleDuplicatedException.class)
			.hasMessageContaining(HandleErrorCode.DUPLICATED.getMessage());
	}

	@DisplayName("Memebr의 상태가 DELETED이다.")
	@Test
	void check_member_status_deleted() {
		Member member = mock(Member.class);
		when(member.getActivateStatus()).thenReturn(ActivateStatus.DELETED);

		assertThatThrownBy(() -> sut.checkActivated(member))
			.isInstanceOf(ActivateStatusException.class)
			.hasMessageContaining(ActivateStatusErrorCode.DELETED.getMessage());

	}

	@DisplayName("Memebr의 상태가 BLOCKED이다.")
	@Test
	void check_member_status_blocked() {
		Member member = mock(Member.class);
		when(member.getActivateStatus()).thenReturn(ActivateStatus.BLOCKED);

		assertThatThrownBy(() -> sut.checkActivated(member))
			.isInstanceOf(ActivateStatusException.class)
			.hasMessageContaining(ActivateStatusErrorCode.BLOCKED.getMessage());
	}

	@DisplayName("Memebr의 상태가 정상(ACTIVE)이다.")
	@Test
	void check_member_status_activated() {
		Member member = mock(Member.class);
		when(member.getActivateStatus()).thenReturn(ActivateStatus.ACTIVE);

		assertThatNoException().isThrownBy(() -> sut.checkActivated(member));
	}
}
