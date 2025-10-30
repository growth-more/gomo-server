package com.gomo.app.core.member.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.domain.exception.HandleDuplicatedException;
import com.gomo.app.core.member.domain.exception.code.HandleErrorCode;
import com.gomo.app.core.member.domain.model.Handle;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.repository.MemberRepository;
import com.gomo.app.core.member.fixture.MemberFixture;

@DisplayName("[Application unit]: 핸들 업데이트 테스트")
@ExtendWith(MockitoExtension.class)
public class HandleServiceTest {

	@InjectMocks
	private HandleService sut;

	@Mock
	private MemberService memberService;

	@Mock
	private MemberRepository memberRepository;

	private final static String UPDATED_HANDLE = "@updatedhandle";

	@DisplayName("핸들 업데이트를 성공한다")
	@Test
	void update_handle_success() {
		Member member = MemberFixture.create();
		doReturn(member).when(memberService).findById(member.getId());
		doReturn(Optional.empty()).when(memberRepository).findByHandle(any(Handle.class));

		sut.update(member.getId(), UPDATED_HANDLE);

		assertThat(member.handle()).isEqualTo(UPDATED_HANDLE);
	}

	@DisplayName("핸들이 중복되지 않는다.")
	@Test
	void validate_non_duplicated_handle() {
		doReturn(Optional.empty()).when(memberRepository).findByHandle(any(Handle.class));

		assertThatCode(() -> sut.validateDuplicated("@nonexistent")).doesNotThrowAnyException();
	}

	@DisplayName("핸들이 중복된다.")
	@Test
	void validate_duplicated_handle() {
		doReturn(Optional.of(mock(Member.class))).when(memberRepository).findByHandle(any(Handle.class));

		assertThatThrownBy(() -> sut.validateDuplicated("@gomo"))
			.isInstanceOf(HandleDuplicatedException.class)
			.hasMessageContaining(HandleErrorCode.DUPLICATED.getMessage());
	}
}
