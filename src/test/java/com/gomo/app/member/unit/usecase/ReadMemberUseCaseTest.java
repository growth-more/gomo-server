package com.gomo.app.member.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.application.port.dto.MemberDto;
import com.gomo.app.core.member.application.usecase.ReadMemberUseCase;
import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.model.MemberId;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.core.point.domain.model.Balance;
import com.gomo.app.core.point.domain.model.TransactorId;
import com.gomo.app.core.point.domain.service.PointWalletService;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Application unit]: 멤버 조회 테스트")
public class ReadMemberUseCaseTest {

	@InjectMocks
	ReadMemberUseCase sut;

	@Mock
	private MemberService memberService;

	@Mock
	private PointWalletService pointWalletService;

	private static final int BALANCE = 5000;

	@DisplayName("멤버 조회에 성공한다")
	@Test
	void read_member_successfully() {
		Member member = MemberFixture.member();
		Balance balance = Balance.of(BALANCE);
		MemberDto expected = MemberDto.from(member, BALANCE);

		doReturn(member).when(memberService).find(any(MemberId.class));
		doReturn(balance).when(pointWalletService).findBalance(any(TransactorId.class));

		MemberDto actual = sut.find(member.uuid());

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}
}
