package com.gomo.app.member.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.member.application.ReadMemberUseCase;
import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.member.presentation.response.ReadMemberResponse;
import com.gomo.app.point.domain.model.Balance;
import com.gomo.app.point.domain.model.TransactorId;
import com.gomo.app.point.domain.service.PointWalletService;

@DisplayName("[Application Unit]: 멤버 조회 테스트")
@ExtendWith(MockitoExtension.class)
public class ReadMemberUseCaseTest {
	@InjectMocks
	private ReadMemberUseCase sut;

	@Mock
	private MemberService memberService;

	@Mock
	private PointWalletService pointWalletService;

	private static final int BALANCE_AMOUNT = 5000;

	@DisplayName("멤버 조회에 성공한다.")
	@Test
	void read_member_successfully() {
		Member member = MemberFixture.member();
		Balance balance = Balance.of(BALANCE_AMOUNT);
		ReadMemberResponse expected = ReadMemberResponse.of(member, BALANCE_AMOUNT);

		doReturn(member).when(memberService).find(MemberId.of(member.uuid()));
		doReturn(balance).when(pointWalletService).findBalance(TransactorId.of(member.getId().getId()));

		ReadMemberResponse actual = sut.find(member.uuid());

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);

	}
}
