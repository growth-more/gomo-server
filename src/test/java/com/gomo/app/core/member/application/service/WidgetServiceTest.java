package com.gomo.app.core.member.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.fixture.MemberFixture;

@DisplayName("[Application unit]: 위젯 스냅샷 수정 테스트")
@ExtendWith(MockitoExtension.class)
class WidgetServiceTest {

	@InjectMocks
	private WidgetService sut;

	@Mock
	private MemberService memberService;

	@DisplayName("위젯 스냅샷을 수정한다.")
	@Test
	void update_widget_snapshot() {
		String updatedSnapshot = "{ \"test\": \"test_snapshot\" }";
		Member member = MemberFixture.create();
		doReturn(member).when(memberService).findById(member.getId());

		sut.update(member.getId(), updatedSnapshot);

		assertThat(member.getWidget().getSnapshot()).isEqualTo(updatedSnapshot);
	}
}
