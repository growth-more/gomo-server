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

import com.gomo.app.common.jwt.port.VerifyJwtPortIn;
import com.gomo.app.core.member.application.adapter.PasswordAdapter;
import com.gomo.app.core.member.application.port.command.CreateMemberCommand;
import com.gomo.app.core.member.common.fixture.MemberFixture;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.repository.MemberRepository;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.core.point.application.port.CreatePointWalletPortIn;
import com.gomo.app.core.point.domain.model.PointWallet;
import com.gomo.app.core.point.domain.model.PointWalletId;
import com.gomo.app.core.point.domain.model.TransactorId;
import com.gomo.app.core.streak.application.port.CreateAchieverPortIn;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Application unit] : 멤버 생성 테스트")
public class CreateMemberUseCaseTest {

	@InjectMocks
	CreateMemberUseCase sut;

	@Mock
	VerifyJwtPortIn verifyJwtPortIn;

	@Mock
	PasswordAdapter passwordAdapter;

	@Mock
	MemberService memberService;

	@Mock
	MemberRepository memberRepository;

	@Mock
	CreatePointWalletPortIn createPointWalletPortIn;

	@Mock
	CreateAchieverPortIn createAchieverPortIn;

	@DisplayName("회원을 등록한다")
	@Test
	void create_member() {
		Member member = MemberFixture.member();
		PointWalletId pointWalletId = PointWalletId.of(UUID.randomUUID());
		PointWallet pointWallet = PointWallet.createDefault(pointWalletId, TransactorId.of(member.id()));
		doNothing().when(memberService).checkEmailDuplicated(any());
		doNothing().when(memberService).checkHandleDuplicated(any());
		doReturn(member.getPassword().getPassword()).when(passwordAdapter).encode(anyString());
		doReturn(member).when(memberRepository).save(any(Member.class));

		UUID actual = sut.create(CreateMemberCommand.of(
			member.email(), member.password(), member.handle(), member.name(), member.motto(), member.getLoginProvider().name(), "temporaryToken"
		));

		assertThat(actual).isEqualTo(member.id());
		verify(createPointWalletPortIn, times(1)).create(any());
		verify(createAchieverPortIn, times(1)).create(any());
	}
}
