package com.gomo.app.core.member.application.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.application.port.dto.MemberDto;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.core.member.fixture.MemberFixture;
import com.gomo.app.core.point.application.port.ReadBalancePortIn;

@DisplayName("[Application unit]: 멤버 조회 테스트")
@ExtendWith(MockitoExtension.class)
public class ReadMemberUseCaseTest {

	@InjectMocks
	private ReadMemberUseCase sut;

	@Mock
	private MemberService memberService;

	@Mock
	private ReadBalancePortIn readBalancePortIn;

	private static final int BALANCE = 5000;

	@DisplayName("멤버 조회에 성공한다")
	@Test
	void read_member_successfully() {
		Member member = MemberFixture.create();
		MemberDto expected = MemberDto.from(member, BALANCE);

		doReturn(member).when(memberService).find(any());
		doReturn(BALANCE).when(readBalancePortIn).find(any());

		MemberDto actual = sut.find(member.getId());

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}
}
