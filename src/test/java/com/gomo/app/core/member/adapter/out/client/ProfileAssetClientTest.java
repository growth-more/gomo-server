package com.gomo.app.core.member.adapter.out.client;

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

import com.gomo.app.support.image.application.port.in.ImageUploader;

@DisplayName("[Adapter unit]: 프로필 관련 에셋 업로드 테스트")
@ExtendWith(MockitoExtension.class)
class ProfileAssetClientTest {

	@InjectMocks
	private ProfileAssetClient sut;

	@Mock
	private ImageUploader imageUploader;

	@DisplayName("이미지를 업로드한다.")
	@Test
	void upload_image() {
		String imageUrl = "image_url";
		doReturn(Optional.of(imageUrl)).when(imageUploader).upload(any());

		String actual = sut.upload(new MockMultipartFile("image", "image.jpg", "image/jpeg", "image/png".getBytes()));

		assertThat(actual).isEqualTo(imageUrl);
	}

	@DisplayName("이미지를 업로드하지 못한다.")
	@Test
	void upload_image_with_null() {
		doReturn(Optional.empty()).when(imageUploader).upload(any());

		String actual = sut.upload(null);

		assertThat(actual).isNull();
	}
}
