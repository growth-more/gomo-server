package com.gomo.app.core.member.application.port.dto;

public record VerifyEmailAuthCodeDto(String email) {

	public static VerifyEmailAuthCodeDto of(String email) {
		return new VerifyEmailAuthCodeDto(email);
	}
}
