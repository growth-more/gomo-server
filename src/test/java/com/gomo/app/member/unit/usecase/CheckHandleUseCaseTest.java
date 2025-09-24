package com.gomo.app.member.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.application.CheckHandleUseCase;
import com.gomo.app.core.member.domain.model.Handle;
import com.gomo.app.core.member.domain.service.MemberService;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Application unit] : 유저 핸들 중복체크 테스트")
public class CheckHandleUseCaseTest {

	@InjectMocks
	private CheckHandleUseCase sut;

	@Mock
	private MemberService memberService;

	@DisplayName("핸들 중복을 확인한다.")
	@Test
	void check_handle_dupliated() {
		String testHandle = "@testhandle";

		doNothing().when(memberService).checkHandleDuplicated(any(Handle.class));
		
		assertThatCode(() -> sut.checkHandleDuplicated(testHandle)).doesNotThrowAnyException();
	}

}
