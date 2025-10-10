package com.gomo.app.core.interest.domain.service;

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

import com.gomo.app.core.interest.domain.repository.InterestRepository;

@DisplayName("[Domain unit]: 로고 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class LogoServiceTest {

	@InjectMocks
	private LogoService sut;

	@Mock
	private InterestRepository interestRepository;

	@DisplayName("모든 로고 url을 조회한다.")
	@Test
	void find_all_logo_url() {
		doReturn(List.of("logoA", "logoB", "logoC")).when(interestRepository).findAllLogoUrl();

		Set<String> urls = sut.findAllUrls();

		assertThat(urls.size()).isEqualTo(3);
	}
}
