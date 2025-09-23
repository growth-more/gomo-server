package com.gomo.app.interest.application.port.command;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public record CreateInterestCommand(UUID registrantId, String name, String colorCode, MultipartFile logoFile) {

	public static CreateInterestCommand of(UUID registrantId, String name, String colorCode, MultipartFile logoFile) {
		return new CreateInterestCommand(registrantId, name, colorCode, logoFile);
	}
}
