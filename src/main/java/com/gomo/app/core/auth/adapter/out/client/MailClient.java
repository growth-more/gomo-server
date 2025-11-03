package com.gomo.app.core.auth.adapter.out.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.core.auth.application.port.out.AuthCodeSender;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class MailClient implements AuthCodeSender {

	@Value("${spring.mail.username}")
	String USERNAME;
	private final JavaMailSender mailSender;

	// TODO [2025-10-10] jhl221123 : 빈번한 요청에 대비해야 합니다.
	@Override
	public void send(String email, String authCode) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(USERNAME);
			helper.setTo(email);
			helper.setSubject("EMAIL 인증코드");
			helper.setText("인증코드는 : " + authCode + "입니다.");
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			throw new IllegalStateException("Failed to Send Auth Code. Try this at later");
		}
	}
}
