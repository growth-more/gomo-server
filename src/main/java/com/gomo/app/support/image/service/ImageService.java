package com.gomo.app.support.image.service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.support.image.exception.ImageErrorCode;
import com.gomo.app.support.image.exception.ImageProcessingException;
import com.gomo.app.support.image.port.DeleteImagePortIn;
import com.gomo.app.support.image.port.ReadImagePortIn;
import com.gomo.app.support.image.port.UploadImagePortIn;

import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.Result;
import io.minio.errors.MinioException;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class ImageService implements UploadImagePortIn, ReadImagePortIn, DeleteImagePortIn {

	private final MinioClient minioClient;

	@Value("${minio.endpoint}")
	private String endpoint;

	@Value("${minio.bucket-name}")
	private String bucketName;

	@Override
	public Optional<String> upload(MultipartFile file) {
		if (file == null || file.isEmpty()) {
			return Optional.empty();
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
			throw new ImageProcessingException(ImageErrorCode.UPLOAD_FAIL, e);
		}

		return Optional.of(String.format("%s/%s/%s", endpoint, bucketName, fileName));
	}

	@Override
	public Set<String> readAllImages() {
		Set<String> images = new HashSet<>();
		try {
			Iterable<Result<Item>> results = minioClient.listObjects(
				ListObjectsArgs.builder().bucket(bucketName).recursive(true).build()
			);
			for (Result<Item> result : results) {
				images.add(getImageUrl(result.get().objectName()));
			}
		} catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
			throw new ImageProcessingException(ImageErrorCode.READ_FAIL, e);
		}
		return images;
	}

	public void delete(String imageUrl) {
		try {
			String fileName = extractFileNameFromUrl(imageUrl);
			minioClient.removeObject(
				RemoveObjectArgs.builder()
					.bucket(bucketName)
					.object(fileName)
					.build()
			);
		} catch (Exception e) {
			throw new ImageProcessingException(ImageErrorCode.DELETE_FAIL, e);
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

	private String getImageUrl(String imageName) {
		return endpoint + "/" + bucketName + "/" + imageName;
	}

	@NotNull
	private String extractFileNameFromUrl(String imageUrl) {
		return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
	}
}
