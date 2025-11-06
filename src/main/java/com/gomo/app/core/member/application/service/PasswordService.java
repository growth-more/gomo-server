package com.gomo.app.core.member.application.service;

import static com.gomo.app.core.member.domain.exception.code.MemberErrorCode.*;

import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.core.member.application.port.in.PasswordResetter;
import com.gomo.app.core.member.application.port.in.PasswordUpdater;
import com.gomo.app.core.member.application.port.out.PasswordEncodeManager;
import com.gomo.app.core.member.domain.exception.MemberAuthenticationFailedException;
import com.gomo.app.core.member.domain.model.LoginProvider;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.model.Password;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class PasswordService implements PasswordResetter, PasswordUpdater {

	private final PasswordEncodeManager passwordEncodeManager;
	private final MemberService memberService;

	@Override
	@AuditLog(action = "PASSWORD_RESET")
	public void reset(String email, String newPassword) {
		Member member = memberService.findByEmail(email);
		String encoded = passwordEncodeManager.encode(Password.ofRaw(newPassword).getPassword());
		member.updatePassword(Password.ofEncoded(encoded));
	}

	@Override
	@AuditLog(action = "PASSWORD_UPDATE")
	public void update(UUID memberId, String originPassword, String newPassword) {
		Member member = memberService.findById(memberId);
		verify(member, originPassword);
		String encoded = passwordEncodeManager.encode(Password.ofRaw(newPassword).getPassword());
		member.updatePassword(Password.ofEncoded(encoded));
	}

	void verify(Member member, String originPassword) {
		if (!passwordEncodeManager.verify(Password.ofRaw(originPassword).getPassword(), member.password())) {
			throw new MemberAuthenticationFailedException(AUTHENTICATION_FAILED);
		}
	}

	Password encode(String loginProvider, String rawPassword, UUID memberId) {
		Password verifiedPassword = LoginProvider.EMAIL.name().equals(loginProvider) ? Password.ofRaw(rawPassword) : Password.forOAuth(memberId.toString());
		return Password.ofEncoded(passwordEncodeManager.encode(verifiedPassword.getPassword()));
	}
}
