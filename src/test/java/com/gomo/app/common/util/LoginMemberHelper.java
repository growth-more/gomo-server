package com.gomo.app.common.util;

import com.gomo.app.member.presentation.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.gomo.app.member.presentation.LoginMemberApi;
import com.gomo.app.member.presentation.request.LoginMemberRequest;
import com.gomo.app.member.presentation.response.LoginMemberResponse;

// TODO <jhl221123>: JWT 도입 이후, 사용하지 않기 때문에 삭제한다.
@Component
public class LoginMemberHelper {

	@Autowired
	LoginMemberApi loginMemberApi;

	public String getSessionId(String email, String password) {
		ResponseEntity<TokenResponse> response = loginMemberApi.login(LoginMemberRequest.of(email, password));
		return response.getBody().getId().toString();
	}
}
