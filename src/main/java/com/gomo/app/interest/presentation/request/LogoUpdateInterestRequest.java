package com.gomo.app.interest.presentation.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;

@Getter
public class LogoUpdateInterestRequest {

	private MultipartFile updatedLogo;

	private LogoUpdateInterestRequest(MultipartFile updatedLogo) {
		this.updatedLogo = updatedLogo;
	}

	public static LogoUpdateInterestRequest of(MultipartFile updatedLogo) {
		return new LogoUpdateInterestRequest(updatedLogo);
	}
}
