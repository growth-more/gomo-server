package com.gomo.app.support.auth.application.adapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.gomo.app.support.auth.application.port.SendAuthCodePortOut;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SendAuthCodeAdapter implements SendAuthCodePortOut {

	@Value("${spring.mail.username}")
	String USERNAME;
	private final JavaMailSender mailSender;

	@Override
	public void toEmail(String email, String authCode) {
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
