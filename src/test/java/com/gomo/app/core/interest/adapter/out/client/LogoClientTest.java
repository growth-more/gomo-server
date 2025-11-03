package com.gomo.app.core.interest.adapter.out.client;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.gomo.app.support.image.application.port.in.ImageDeleter;
import com.gomo.app.support.image.application.port.in.ImageUploader;

@DisplayName("[Adapter unit]: 이미지 업로드 테스트")
@ExtendWith(MockitoExtension.class)
class LogoClientTest {

	@InjectMocks
	private LogoClient sut;

	@Mock
	private ImageUploader imageUploader;

	@Mock
	private ImageDeleter imageDeleter;

	@DisplayName("이미지를 업로드한다.")
	@Test
	void upload_image() {
		String imageUrl = "image_url";
		doReturn(Optional.of(imageUrl)).when(imageUploader).upload(any());

		Optional<String> actual = sut.upload(new MockMultipartFile("image", "image.jpg", "image/jpeg", "image/png".getBytes()));

		assertThat(actual.get()).isEqualTo(imageUrl);
	}

	@DisplayName("이미지를 삭제한다.")
	@Test
	void delete_image() {
		sut.delete("imageUrl");

		verify(imageDeleter, times(1)).delete(any());
	}
}
