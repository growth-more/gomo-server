package com.gomo.app.core.interest.presentation.request;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.core.interest.application.port.command.CreateInterestCommand;

import lombok.Getter;

@Getter
public class CreateInterestRequest {

	private String name;
	private String colorCode;
	private MultipartFile logoFile;

	private CreateInterestRequest() {
	}

	public CreateInterestRequest(String name, String colorCode, MultipartFile logoFile) {
		this.name = name;
		this.colorCode = colorCode;
		this.logoFile = logoFile;
	}

	public static CreateInterestRequest of(String name, String colorCode, MultipartFile logoFile) {
		return new CreateInterestRequest(name, colorCode, logoFile);
	}

	public CreateInterestCommand toCommand(UUID registrantId) {
		return CreateInterestCommand.of(registrantId, name, colorCode, logoFile);
	}
}
