package com.gomo.app.interest.application.adapter;

import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.common.Adapter;
import com.gomo.app.image.port.UploadImagePortIn;
import com.gomo.app.image.port.dto.UploadImageDto;
import com.gomo.app.interest.application.port.UploadLogoPortOut;
import com.gomo.app.interest.application.port.dto.LogoDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class UploadLogoAdapter implements UploadLogoPortOut {

	private final UploadImagePortIn uploadImagePortIn;

	@Override
	public LogoDto upload(MultipartFile logoFile) {
		UploadImageDto uploadImageDto = uploadImagePortIn.upload(logoFile);
		return LogoDto.of(uploadImageDto.url());
	}
}
