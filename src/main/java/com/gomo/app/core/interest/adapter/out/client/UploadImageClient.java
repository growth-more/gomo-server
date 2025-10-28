package com.gomo.app.core.interest.adapter.out.client;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.core.interest.application.port.out.UploadLogoPort;
import com.gomo.app.support.image.application.port.UploadImagePortIn;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class UploadImageClient implements UploadLogoPort {

	private final UploadImagePortIn uploadImagePortIn;

	@Override
	public Optional<String> upload(MultipartFile logo) {
		return uploadImagePortIn.upload(logo);
	}
}
