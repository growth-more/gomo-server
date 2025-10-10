package com.gomo.app.support.image.application.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.gomo.app.support.image.application.port.ManageImagePortOut;

@DisplayName("[Application Unit]: 이미지 파일 업로드 테스트")
@ExtendWith(MockitoExtension.class)
class UploadImageUseCaseTest {

	@InjectMocks
	private UploadImageUseCase sut;

	@Mock
	private ManageImagePortOut manageImagePortOut;

	@DisplayName("이미지를 업로드한다.")
	@Test
	void upload_image() {
		MockMultipartFile file = new MockMultipartFile("file", "image.jpg", "image/jpeg", "image/jpeg".getBytes());
		String imageUrl = "imageUrl";
		doReturn(imageUrl).when(manageImagePortOut).save(any());

		Optional<String> actual = sut.upload(file);

		assertThat(actual).isNotEmpty();
		assertThat(actual.get()).isEqualTo(imageUrl);
	}

	@DisplayName("비어있는 파일을 업로드한다.")
	@Test
	void upload_empty_file() {
		Optional<String> actual = sut.upload(null);

		assertThat(actual).isEmpty();
	}
}
