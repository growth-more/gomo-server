package com.gomo.app.support.image.application.port.out;

import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.support.image.exception.ImageProcessingException;

public interface ImageStore {

	/**
	 * Saves the given image file to the underlying storage.
	 *
	 * @param file The multipart file to be saved. Must not be null.
	 * @return The publicly accessible URL of the saved image.
	 * @throws ImageProcessingException if the upload fails due to I/O errors or other storage-related issues.
	 */
	String save(MultipartFile file);

	/**
	 * Retrieves the URLs of all images currently available in the storage.
	 *
	 * @return A set containing the URLs of all images. Returns an empty set if no images are found.
	 * @throws ImageProcessingException if reading from the storage fails.
	 */
	Set<String> findAllImageUrls();

	/**
	 * Deletes an image from the storage based on its publicly accessible URL.
	 *
	 * @param fileUrl The URL of the image to be deleted.
	 * @throws ImageProcessingException if the deletion fails.
	 */
	void delete(String fileUrl);
}
