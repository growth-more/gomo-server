package com.gomo.app.interest.application.port.dto;

public record LogoDto(String url) {

	public static LogoDto of(String url) {
		return new LogoDto(url);
	}
}
