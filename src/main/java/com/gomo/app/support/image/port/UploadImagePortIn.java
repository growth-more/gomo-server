package com.gomo.app.support.image.port;

import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.support.image.port.dto.UploadImageDto;

public interface UploadImagePortIn {

	UploadImageDto upload(MultipartFile file);
}
