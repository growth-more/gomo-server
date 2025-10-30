package com.gomo.app.core.member.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.application.port.command.CreateMemberCommand;
import com.gomo.app.core.member.application.port.out.EmailTokenVerifier;
import com.gomo.app.core.member.application.port.out.MemberCreateEventPublisher;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.repository.MemberRepository;
import com.gomo.app.core.member.fixture.MemberFixture;

@DisplayName("[Application unit] : 멤버 생성 테스트")
@ExtendWith(MockitoExtension.class)
public class MemberCreateServiceTest {

	@InjectMocks
	private MemberCreateService sut;

	@Mock
	private EmailTokenVerifier emailTokenVerifier;

	@Mock
	private PasswordService passwordService;

	@Mock
	private EmailService emailService;

	@Mock
	private HandleService handleService;

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private MemberCreateEventPublisher memberCreateEventPublisher;

	@DisplayName("회원을 등록한다")
	@Test
	void create_member() {
		Member member = MemberFixture.create();
		doReturn(member.getPassword()).when(passwordService).encode(any(), any(), any());
		doReturn(member).when(memberRepository).save(any(Member.class));

		UUID actual = sut.create(CreateMemberCommand.of(
			member.email(), member.password(), member.handle(), member.name(), member.motto(), member.getLoginProvider().name(), "temporaryToken"
		));

		assertThat(actual).isEqualTo(member.getId());
		verify(emailTokenVerifier, times(1)).verify(any());
		verify(emailService, times(1)).validateDuplicated(any());
		verify(handleService, times(1)).validateDuplicated(any());
		verify(memberCreateEventPublisher, times(1)).publish(any());
	}
}
