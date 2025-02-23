package com.gomo.app.member.application;

import java.util.Random;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.member.domain.service.MemberValidator;
import com.gomo.app.member.infrastructure.EmailAuthRedisService;
import com.gomo.app.member.infrastructure.EmailAuthSenderService;
import com.gomo.app.member.presentation.request.CreateEmailAuthCodeRequest;
import com.gomo.app.member.presentation.response.CreateEmailAuthCodeResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CreateEmailAuthCodeUseCase {

	private final EmailAuthRedisService emailAuthRedisService;
	private final EmailAuthSenderService emailAuthSenderService;
	private final MemberValidator memberValidator;

	public CreateEmailAuthCodeResponse create(CreateEmailAuthCodeRequest request) {

		memberValidator.checkDuplicatedEmail(request.getEmail());

		String authCode = generateAuthCode();
		emailAuthRedisService.setAuthCode(request.getEmail(), authCode);

		// TODO <jhl221123 to nurdykim>: 운영 환경과 테스트 환경을 분리하면 좋을 것 같습니다.
		emailAuthSenderService.sendEmailAuthCode(request.getEmail(), authCode);

		return CreateEmailAuthCodeResponse.of(request.getEmail());
	}

	private String generateAuthCode(){
		return String.valueOf(new Random().nextInt(900000) + 100000);
	}
}
