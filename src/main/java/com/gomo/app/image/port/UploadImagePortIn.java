package com.gomo.app.image.port;

import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.image.port.dto.UploadImageDto;

public interface UploadImagePortIn {

	UploadImageDto upload(MultipartFile file);
}
