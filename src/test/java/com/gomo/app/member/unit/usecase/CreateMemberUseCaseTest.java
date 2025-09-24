package com.gomo.app.member.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.application.CreateMemberUseCase;
import com.gomo.app.core.member.application.port.command.CreateMemberCommand;
import com.gomo.app.core.member.application.port.dto.CreateMemberDto;
import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.repository.MemberRepository;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.core.member.domain.service.PasswordService;
import com.gomo.app.core.point.domain.model.PointWallet;
import com.gomo.app.core.point.domain.model.PointWalletId;
import com.gomo.app.core.point.domain.model.TransactorId;
import com.gomo.app.core.point.domain.repository.PointWalletRepository;
import com.gomo.app.core.streak.domain.service.AchieverService;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Application unit] : 멤버 생성 테스트")
public class CreateMemberUseCaseTest {

	@InjectMocks
	CreateMemberUseCase sut;

	@Mock
	MemberService memberService;

	@Mock
	PasswordService passwordService;

	@Mock
	MemberRepository memberRepository;

	@Mock
	PointWalletRepository pointWalletRepository;

	@Mock
	AchieverService achieverService;

	@DisplayName("회원을 등록한다")
	@Test
	void create_member() {
		Member member = MemberFixture.member();
		PointWalletId pointWalletId = PointWalletId.of(UUID.randomUUID());
		PointWallet pointWallet = PointWallet.createDefault(pointWalletId, TransactorId.of(member.uuid()));

		doNothing().when(memberService).checkEmailDuplicated(any());
		doNothing().when(memberService).checkHandleDuplicated(any());
		doReturn(member.getPassword().getPassword()).when(passwordService).encode(anyString());
		doReturn(member).when(memberRepository).save(any(Member.class));
		doReturn(pointWallet).when(pointWalletRepository).save(any(PointWallet.class));

		CreateMemberDto dto = sut.create(CreateMemberCommand.of(
			member.getEmail().getEmail(),
			member.getPassword().getPassword(),
			member.getHandle().getHandle(),
			member.getName().getName(),
			member.getMotto().getMotto(),
			member.getLoginProvider().name()
		));

		assertThat(dto.id()).isEqualTo(member.uuid());
	}
}
