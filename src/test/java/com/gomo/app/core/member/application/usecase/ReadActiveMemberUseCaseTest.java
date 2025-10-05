package com.gomo.app.core.member.application.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.application.port.dto.ActiveMemberDto;
import com.gomo.app.core.member.domain.repository.MemberRepository;
import com.gomo.app.core.member.fixture.MemberFixture;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Application unit]: 활성화 멤버 조회 테스트")
class ReadActiveMemberUseCaseTest {

	@InjectMocks
	private ReadActiveMemberUseCase sut;

	@Mock
	private MemberRepository memberRepository;

	@DisplayName("활성화 멤버 조회에 성공한다")
	@Test
	void read_active_member() {
		doReturn(List.of(MemberFixture.member(), MemberFixture.member())).when(memberRepository).findByActivateStatusAndLastLoginDateTimeGreaterThanEqual(any(), any());
		List<ActiveMemberDto> actual = sut.findAll(LocalDate.now());
		assertThat(actual.size()).isEqualTo(2);
	}
}
