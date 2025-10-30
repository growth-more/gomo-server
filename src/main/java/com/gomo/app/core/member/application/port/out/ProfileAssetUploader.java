package com.gomo.app.core.member.application.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface ProfileAssetUploader {

	/**
	 * Uploads a given image file, typically for a member's profile or banner.
	 *
	 * @param file The image file to be uploaded.
	 * @return The URL of the uploaded image as a {@link String}. Returns null if the file is empty or null.
	 */
	String upload(MultipartFile file);
}
