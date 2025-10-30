package com.gomo.app.support.image.application.port;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploader {

	/**
	 * Uploads a new image file.
	 *
	 * @param file The image file to upload.
	 * @return An {@link Optional} containing the public URL of the uploaded image,
	 *         or an empty Optional if the provided file is null or empty.
	 */
	Optional<String> upload(MultipartFile file);
}
