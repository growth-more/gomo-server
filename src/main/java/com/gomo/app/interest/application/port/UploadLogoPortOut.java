package com.gomo.app.interest.application.port;

import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.interest.application.port.dto.LogoDto;

public interface UploadLogoPortOut {

	LogoDto upload(MultipartFile logoFile);
}
