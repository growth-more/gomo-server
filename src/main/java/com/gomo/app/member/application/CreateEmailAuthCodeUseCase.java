package com.gomo.app.member.application;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.member.domain.service.EmailAuthCodeService;
import com.gomo.app.member.presentation.request.CreateEmailAuthCodeRequest;
import com.gomo.app.member.presentation.response.CreateEmailAuthCodeResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CreateEmailAuthCodeUseCase {

	private final EmailAuthCodeService emailAuthCodeService;

	public CreateEmailAuthCodeResponse create(CreateEmailAuthCodeRequest request) {
		return null;
	}
}
