package com.gomo.app.support.image.infrastructure;

import static com.gomo.app.test.container.MinioContainerInitializer.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import com.gomo.app.test.IntegrationTest;
import com.gomo.app.test.WithMinio;

@DisplayName("[Infrastructure integration]: minio 시나리오 테스트")
@IntegrationTest
@WithMinio
class MinioImageAdapterTest {

	@Autowired
	private MinioImageAdapter minioImageAdapter;
	private String uploadedImageUrl;

	@DisplayName("시나리오: 이미지 업로드, 조회, 삭제")
	@TestFactory
	Collection<DynamicNode> imageManagementDynamicScenario() {
		MockMultipartFile mockFile = new MockMultipartFile("image", "test.png", "image/png", "test-data".getBytes());

		return List.of(
			dynamicTest("1단계: 새로운 이미지를 업로드한다", () -> {
				String imageUrl = minioImageAdapter.save(mockFile);

				assertThat(imageUrl).isNotNull();
				assertThat(imageUrl).contains(TEST_BUCKET_NAME, ".png");

				this.uploadedImageUrl = imageUrl;
			}),

			dynamicTest("2단계: 업로드된 이미지가 전체 목록에서 조회된다", () -> {
				assertThat(this.uploadedImageUrl).as("1단계(업로드)가 먼저 성공해야 한다.").isNotNull();

				Set<String> allImageUrls = minioImageAdapter.findAllImageUrls();

				assertThat(allImageUrls).hasSize(1).contains(this.uploadedImageUrl);
			}),

			dynamicTest("3단계: 이미지를 삭제하고, 목록에서 사라졌는지 확인한다", () -> {
				assertThat(this.uploadedImageUrl).as("1단계(업로드)가 먼저 성공해야 한다.").isNotNull();

				assertDoesNotThrow(() -> minioImageAdapter.delete(this.uploadedImageUrl));

				Set<String> urlsAfterDeletion = minioImageAdapter.findAllImageUrls();
				assertThat(urlsAfterDeletion).isEmpty();
			})
		);
	}
}
