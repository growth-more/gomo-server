package com.gomo.app.support.image.port;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public interface UploadImagePortIn {

	/**
	 * @return image URL, or Optional.empty() if none
	 */
	Optional<String> upload(MultipartFile file);
}
