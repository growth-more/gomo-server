package com.gomo.app.support.image.application.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.support.image.application.port.ManageImagePortOut;

@DisplayName("[Application Unit]: 이미지 파일 조회 테스트")
@ExtendWith(MockitoExtension.class)
class ReadImageUseCaseTest {

	@InjectMocks
	private ReadImageUseCase sut;

	@Mock
	private ManageImagePortOut manageImagePortOut;

	@DisplayName("이미지 목록을 조회한다.")
	@Test
	void read_images() {
		doReturn(Set.of("urlA", "urlB")).when(manageImagePortOut).findAllImageUrls();

		Set<String> actual = sut.readAllImages();

		assertThat(actual.size()).isEqualTo(2);
	}
}
