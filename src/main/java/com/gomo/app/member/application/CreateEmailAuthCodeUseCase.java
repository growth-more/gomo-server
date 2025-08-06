package com.gomo.app.member.application;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.repository.EmailAuthCodeRepository;
import com.gomo.app.member.domain.service.AuthCodeGenerator;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.member.infrastructure.EmailAuthSenderService;
import com.gomo.app.auth.presentation.request.CreateEmailAuthCodeRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CreateEmailAuthCodeUseCase {

	private final MemberService memberService;
	private final AuthCodeGenerator authCodeGenerator;
	private final EmailAuthSenderService emailAuthSenderService;
	private final EmailAuthCodeRepository emailAuthCodeRepository;

	public void createForSignUp(CreateEmailAuthCodeRequest request) {
		memberService.checkEmailDuplicated(Email.of(request.getEmail()));
		String authCode = authCodeGenerator.generate();
		emailAuthCodeRepository.save(request.getEmail(), authCode);
		emailAuthSenderService.sendEmailAuthCode(request.getEmail(), authCode);
	}

	public void createForPasswordReset(CreateEmailAuthCodeRequest request) {
		memberService.findByEmail(Email.of(request.getEmail()));
		String authCode = authCodeGenerator.generate();
		emailAuthCodeRepository.save(request.getEmail(), authCode);
		emailAuthSenderService.sendEmailAuthCode(request.getEmail(), authCode);
	}
}
