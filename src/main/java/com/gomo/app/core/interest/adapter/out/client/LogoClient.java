package com.gomo.app.core.interest.adapter.out.client;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.core.interest.application.port.out.LogoDeleter;
import com.gomo.app.core.interest.application.port.out.LogoUploader;
import com.gomo.app.support.image.application.port.ImageDeleter;
import com.gomo.app.support.image.application.port.ImageUploader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class LogoClient implements LogoUploader, LogoDeleter {

	private final ImageUploader imageUploader;
	private final ImageDeleter imageDeleter;

	@Override
	public Optional<String> upload(MultipartFile logo) {
		return imageUploader.upload(logo);
	}

	@Override
	public void delete(String logoUrl) {
		imageDeleter.delete(logoUrl);
	}
}
