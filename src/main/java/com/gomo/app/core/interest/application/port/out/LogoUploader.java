package com.gomo.app.core.interest.application.port.out;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public interface LogoUploader {

	Optional<String> upload(MultipartFile logo);
}
