package com.gomo.app.core.member.application.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.common.security.encoder.application.port.EncodePasswordPortIn;
import com.gomo.app.common.security.jwt.application.port.VerifyJwtPortIn;
import com.gomo.app.core.member.application.port.command.CreateMemberCommand;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.repository.MemberRepository;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.core.member.fixture.MemberFixture;
import com.gomo.app.core.point.application.port.CreatePointWalletPortIn;
import com.gomo.app.core.streak.application.port.CreateAchieverPortIn;

@DisplayName("[Application unit] : 멤버 생성 테스트")
@ExtendWith(MockitoExtension.class)
public class CreateMemberUseCaseTest {

	@InjectMocks
	private CreateMemberUseCase sut;

	@Mock
	private VerifyJwtPortIn verifyJwtPortIn;

	@Mock
	private EncodePasswordPortIn encodePasswordPortIn;

	@Mock
	private MemberService memberService;

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private CreatePointWalletPortIn createPointWalletPortIn;

	@Mock
	CreateAchieverPortIn createAchieverPortIn;

	@DisplayName("회원을 등록한다")
	@Test
	void create_member() {
		Member member = MemberFixture.create();
		doReturn(member.password()).when(encodePasswordPortIn).encode(anyString());
		doReturn(member).when(memberRepository).save(any(Member.class));

		UUID actual = sut.create(CreateMemberCommand.of(
			member.email(), member.password(), member.handle(), member.name(), member.motto(), member.getLoginProvider().name(), "temporaryToken"
		));

		assertThat(actual).isEqualTo(member.getId());
		verify(verifyJwtPortIn, times(1)).validateToken(any());
		verify(memberService, times(1)).checkEmailDuplicated(any());
		verify(memberService, times(1)).checkHandleDuplicated(any());
		verify(createPointWalletPortIn, times(1)).create(any());
		verify(createAchieverPortIn, times(1)).create(any());
	}
}
