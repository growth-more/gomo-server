package com.gomo.app.core.member.adapter.out.client;

import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.core.member.application.port.out.ProfileAssetUploader;
import com.gomo.app.support.image.application.port.ImageUploader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class ProfileAssetClient implements ProfileAssetUploader {

	private final ImageUploader imageUploader;

	@Override
	public String upload(MultipartFile image) {
		return imageUploader.upload(image).orElse(null);
	}
}
