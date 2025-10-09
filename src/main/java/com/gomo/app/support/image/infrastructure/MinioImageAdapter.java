package com.gomo.app.support.image.infrastructure;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.support.image.application.port.ManageImagePortOut;
import com.gomo.app.support.image.exception.ImageErrorCode;
import com.gomo.app.support.image.exception.ImageProcessingException;

import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.Result;
import io.minio.errors.MinioException;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class MinioImageAdapter implements ManageImagePortOut {

	private final MinioClient minioClient;

	@Value("${minio.endpoint}")
	private String endpoint;

	@Value("${minio.bucket-name}")
	private String bucketName;

	@Override
	public String save(MultipartFile file) {
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
			return generateUrl(fileName);
		} catch (Exception e) {
			throw new ImageProcessingException(ImageErrorCode.UPLOAD_FAIL, e);
		}
	}

	@Override
	public Set<String> findAllImageUrls() {
		Set<String> fileUrls = new HashSet<>();
		try {
			Iterable<Result<Item>> results = minioClient.listObjects(
				ListObjectsArgs.builder().bucket(bucketName).recursive(true).build()
			);
			for (Result<Item> result : results) {
				String fileName = result.get().objectName();
				fileUrls.add(generateUrl(fileName));
			}
		} catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
			throw new ImageProcessingException(ImageErrorCode.READ_FAIL, e);
		}
		return fileUrls;
	}

	@Override
	public void delete(String fileUrl) {
		String fileName = extractFileName(fileUrl);
		try {
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
		String filename = file.getOriginalFilename();
		String extension = filename != null && filename.contains(".") ? filename.substring(filename.lastIndexOf(".")) : "";
		return UUID.randomUUID() + extension;
	}

	@NotNull
	private String generateUrl(String objectName) {
		return String.format("%s/%s/%s", endpoint, bucketName, objectName);
	}

	@NotNull
	private String extractFileName(String imageUrl) {
		return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
	}
}
