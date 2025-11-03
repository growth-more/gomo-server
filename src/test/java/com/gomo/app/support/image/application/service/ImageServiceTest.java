package com.gomo.app.support.image.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.gomo.app.support.image.application.port.out.ImageStore;

@DisplayName("[Application Unit]: 이미지 파일 조회 테스트")
@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

	@InjectMocks
	private ImageService sut;

	@Mock
	private ImageStore imageStore;

	@DisplayName("이미지 목록을 조회한다.")
	@Test
	void read_images() {
		doReturn(Set.of("urlA", "urlB")).when(imageStore).findAllImageUrls();

		Set<String> actual = sut.readAllImages();

		assertThat(actual.size()).isEqualTo(2);
	}

	@DisplayName("이미지를 업로드한다.")
	@Test
	void upload_image() {
		MockMultipartFile file = new MockMultipartFile("file", "image.jpg", "image/jpeg", "image/jpeg".getBytes());
		String imageUrl = "imageUrl";
		doReturn(imageUrl).when(imageStore).save(any());

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

	@DisplayName("이미지 파일을 삭제한다.")
	@Test
	void delete_image() {
		sut.delete("imageUrl");

		verify(imageStore, times(1)).delete(anyString());
	}
}
