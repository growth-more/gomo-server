package com.gomo.app.test.container;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MinIOContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;

public class MinioContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	public static final MinIOContainer minioContainer;
	public static final String TEST_BUCKET_NAME = "test-bucket";

	static {
		minioContainer = new MinIOContainer("minio/minio:RELEASE.2023-09-04T19-57-37Z")
			.withExposedPorts(9000)
			.withReuse(true)
			.withUserName("testuser")
			.withPassword("testpassword")
			.waitingFor(Wait.forListeningPort());
		minioContainer.start();
		createBucketIfNotExists();
	}

	@Override
	public void initialize(ConfigurableApplicationContext context) {
		TestPropertyValues.of(
			"minio.endpoint=" + minioContainer.getS3URL(),
			"minio.access-key=" + minioContainer.getUserName(),
			"minio.secret-key=" + minioContainer.getPassword(),
			"minio.bucket-name=" + TEST_BUCKET_NAME
		).applyTo(context.getEnvironment());
	}

	private static void createBucketIfNotExists() {
		try {
			MinioClient tempClient = MinioClient.builder()
				.endpoint(minioContainer.getS3URL())
				.credentials(minioContainer.getUserName(), minioContainer.getPassword())
				.build();

			boolean found = tempClient.bucketExists(BucketExistsArgs.builder().bucket(TEST_BUCKET_NAME).build());
			if (!found) {
				tempClient.makeBucket(MakeBucketArgs.builder().bucket(TEST_BUCKET_NAME).build());
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to create Minio bucket for testing", e);
		}
	}
}
