package com.gomo.app.member.application;

import java.util.Random;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.member.infrastructure.EmailAuthSenderService;
import com.gomo.app.member.infrastructure.repository.EmailAuthRedisRepository;
import com.gomo.app.member.presentation.request.CreateEmailAuthCodeRequest;
import com.gomo.app.member.presentation.response.CreateEmailAuthCodeResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CreateEmailAuthCodeUseCase {

	private final MemberService memberService;
	private final EmailAuthRedisRepository emailAuthRedisRepository;
	private final EmailAuthSenderService emailAuthSenderService;

	public CreateEmailAuthCodeResponse create(CreateEmailAuthCodeRequest request) {
		memberService.checkEmailDuplicated(Email.of(request.getEmail()));
		String authCode = generateAuthCode();
		emailAuthRedisRepository.setAuthCode(request.getEmail(), authCode);
		emailAuthSenderService.sendEmailAuthCode(request.getEmail(), authCode);

		return CreateEmailAuthCodeResponse.of(request.getEmail());
	}

	private String generateAuthCode() {
		return String.valueOf(new Random().nextInt(900000) + 100000);
	}
}
