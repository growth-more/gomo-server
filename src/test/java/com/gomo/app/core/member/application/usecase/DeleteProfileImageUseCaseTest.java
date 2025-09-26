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
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.service.MemberService;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Application Unit]: 멤버 프로필 이미지 삭제 테스트")
public class DeleteProfileImageUseCaseTest {

	@InjectMocks
	DeleteProfileImageUseCase sut;

	@Mock
	MemberService memberService;

	@DisplayName("프로필 이미지 삭제 테스트")
	@Test
	void delete_profile_image() {
		Member member = MemberFixture.member();
		doReturn(member).when(memberService).find(member.getId());
		sut.delete(member.id());
		assertThat(member.profileImageUrl()).isEqualTo("DEFAULT_IMAGE");
	}
}
