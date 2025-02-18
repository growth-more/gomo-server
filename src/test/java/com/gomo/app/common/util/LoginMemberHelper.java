package com.gomo.app.common.util;

import com.gomo.app.member.presentation.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.gomo.app.member.presentation.LoginMemberApi;
import com.gomo.app.member.presentation.request.LoginMemberRequest;

@Component
public class LoginMemberHelper {

	@Autowired
	LoginMemberApi loginMemberApi;

	public String getAccessToken(String email, String password) {
		ResponseEntity<TokenResponse> response = loginMemberApi.login(LoginMemberRequest.of(email, password));
		return response.getBody().getToken().toString();
	}
}
