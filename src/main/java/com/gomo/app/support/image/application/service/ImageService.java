package com.gomo.app.support.image.application.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.support.image.application.port.in.ImageDeleter;
import com.gomo.app.support.image.application.port.in.ImageReader;
import com.gomo.app.support.image.application.port.in.ImageUploader;
import com.gomo.app.support.image.application.port.out.ImageStore;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class ImageService implements ImageReader, ImageUploader, ImageDeleter {

	private final ImageStore imageStore;

	@Override
	public Set<String> readAllImages() {
		return imageStore.findAllImageUrls();
	}

	@Override
	public Optional<String> upload(MultipartFile file) {
		if (file == null || file.isEmpty()) {
			return Optional.empty();
		}
		String fileUrl = imageStore.save(file);
		return Optional.of(fileUrl);
	}

	@Override
	public void delete(String imageUrl) {
		imageStore.delete(imageUrl);
	}
}
