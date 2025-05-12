package com.gomo.app.member.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.member.application.UpdateHandleUseCase;
import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.member.presentation.request.UpdateHandleRequest;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Application unit]: 핸들 업데이트 테스트")
public class UpdateHandleUseCaseTest {

	@InjectMocks
	UpdateHandleUseCase sut;

	@Mock
	private MemberService memberService;

	private final static String UPDATED_HANDLE = "@updatedhandle";

	@DisplayName("핸들 업데이트를 성공한다")
	@Test
	void update_handle_success() {
		Member member = MemberFixture.member();
		UpdateHandleRequest request = UpdateHandleRequest.of(UPDATED_HANDLE);
		doReturn(member).when(memberService).find(member.getId());

		sut.update(member.uuid(), request);

		assertThat(member.getHandle().getHandle()).isEqualTo(UPDATED_HANDLE);
	}

}
