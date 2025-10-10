package com.gomo.app.core.member.domain.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.domain.repository.MemberRepository;

@DisplayName("[Domain unit]: 프로필 이미지 URL 조회 테스트")
@ExtendWith(MockitoExtension.class)
public class ProfileImageServiceTest {

	@InjectMocks
	private ProfileImageService sut;

	@Mock
	private MemberRepository memberRepository;

	@DisplayName("프로필 이미지 URL을 조회한다.")
	@Test
	void find_all_profile_image_url() {
		doReturn(List.of("profileA", "profileB")).when(memberRepository).findAllByProfileImageUrl();

		Set<String> actual = sut.getAllProfileImageUrl();

		assertThat(actual.size()).isEqualTo(2);
	}
}
