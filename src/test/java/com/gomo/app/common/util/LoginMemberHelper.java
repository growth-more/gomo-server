package com.gomo.app.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.gomo.app.support.auth.presentation.AuthMemberApi;
import com.gomo.app.support.auth.presentation.request.LoginMemberRequest;
import com.gomo.app.support.auth.presentation.response.LoginMemberResponse;

@Component
public class LoginMemberHelper {

	@Autowired
	AuthMemberApi authMemberApi;

	public String getAccessToken(String email, String password) {
		ResponseEntity<LoginMemberResponse> response = authMemberApi.login(LoginMemberRequest.of(email, password));
		return response.getBody().getToken().toString();
	}
}
