package com.gomo.app.member.presentation;

import com.gomo.app.common.authentication.Auth;
import com.gomo.app.member.application.LogoutMemberUseCase;
import com.gomo.app.member.application.RefreshAccessTokenUseCase;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.presentation.request.RefreshAccessTokenRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.member.application.LoginMemberUseCase;
import com.gomo.app.member.presentation.request.LoginMemberRequest;
import com.gomo.app.member.presentation.response.LoginMemberResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members")
@Presentation
public class LoginMemberApi {

	private final LoginMemberUseCase loginMemberUseCase;
	private final LogoutMemberUseCase logoutMemberUseCase;
	private final RefreshAccessTokenUseCase refreshAccessTokenUseCase;

	@PostMapping("/login")
	public ResponseEntity<LoginMemberResponse> login(@RequestBody LoginMemberRequest loginMemberRequest) {
		LoginMemberResponse response = loginMemberUseCase.login(loginMemberRequest);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/refresh")
	public ResponseEntity<LoginMemberResponse> refresh(@RequestBody RefreshAccessTokenRequest accessRenweRequest){
		LoginMemberResponse response = refreshAccessTokenUseCase.refresh(accessRenweRequest);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/logout")
	public ResponseEntity<Void> logout(@Auth MemberId memberId){
		logoutMemberUseCase.logout(memberId);
		return ResponseEntity.ok().build();
	}
}
