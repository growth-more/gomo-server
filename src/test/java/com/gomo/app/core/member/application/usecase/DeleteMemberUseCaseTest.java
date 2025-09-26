package com.gomo.app.core.member.application.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.common.fixture.MemberFixture;
import com.gomo.app.core.member.domain.model.ActivateStatus;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.support.auth.application.port.DeleteAuthTokenPortIn;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Application Unit]: 멤버 삭제 테스트")
public class DeleteMemberUseCaseTest {

	@InjectMocks
	DeleteMemberUseCase sut;

	@Mock
	private MemberService memberService;

	@Mock
	private DeleteAuthTokenPortIn deleteAuthTokenPortIn;

	@DisplayName("멤버 삭제 테스트")
	@Test
	void delete_member_successfully() {
		Member member = MemberFixture.member();
		doReturn(member).when(memberService).find(member.getId());
		doNothing().when(deleteAuthTokenPortIn).deleteRefreshToken(member.id());

		sut.delete(member.id());

		assertThat(member.getActivateStatus()).isEqualTo(ActivateStatus.DELETED);
	}

}
