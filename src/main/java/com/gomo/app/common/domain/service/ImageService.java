package com.gomo.app.common.domain.service;

import static com.gomo.app.common.exception.DomainErrorCode.*;

import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.common.exception.ImageProcessingException;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class ImageService {

	private final MinioClient minioClient;

	@Value("${minio.endpoint}")
	private String endpoint;

	@Value("${minio.bucket-name}")
	private String bucketName;

	public String uploadImage(MultipartFile file) {
		if (file == null || file.isEmpty()) {
			return null;
		}

		String fileName = generateUniqueFileName(file);
		try {
			minioClient.putObject(
				PutObjectArgs.builder()
					.bucket(bucketName)
					.object(fileName)
					.stream(file.getInputStream(), file.getSize(), -1)
					.contentType(file.getContentType())
					.build()
			);
		} catch (Exception e) {
			throw new ImageProcessingException(IMAGE_PROCESSING_ERROR, "An error occurred while uploading the image.", e);
		}

		return String.format("%s/%s/%s", endpoint, bucketName, fileName);
	}

	public void deleteImage(String imageUrl) {
		try {
			String fileName = extractFileNameFromUrl(imageUrl);
			minioClient.removeObject(
				RemoveObjectArgs.builder()
					.bucket(bucketName)
					.object(fileName)
					.build()
			);
		} catch (Exception e) {
			throw new ImageProcessingException(IMAGE_PROCESSING_ERROR, "An error occurred while deleting the image.", e);
		}
	}



	@NotNull
	private static String generateUniqueFileName(MultipartFile file) {
		String uuid = UUID.randomUUID().toString();

		String originalFileName = file.getOriginalFilename();
		String extension = "";
		if (originalFileName != null && originalFileName.contains(".")) {
			extension = originalFileName.substring(originalFileName.lastIndexOf("."));
		}

		return uuid + extension;
	}

	@NotNull
	private String extractFileNameFromUrl(String imageUrl) {
		return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
	}
}
