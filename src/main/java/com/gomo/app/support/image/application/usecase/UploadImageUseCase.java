package com.gomo.app.support.image.application.usecase;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.support.image.application.port.ImageUploader;
import com.gomo.app.support.image.application.port.ManageImagePortOut;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class UploadImageUseCase implements ImageUploader {

	private final ManageImagePortOut manageImagePortOut;

	@Override
	public Optional<String> upload(MultipartFile file) {
		if (file == null || file.isEmpty()) {
			return Optional.empty();
		}
		String fileUrl = manageImagePortOut.save(file);
		return Optional.of(fileUrl);
	}
}
