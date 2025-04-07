package com.gomo.app.member.presentation;

import com.gomo.app.common.authentication.Auth;
import com.gomo.app.common.authentication.AuthInfo;
import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.member.application.DeleteMemberUseCase;
import com.gomo.app.member.application.UpdateMemberUseCase;
import com.gomo.app.member.domain.model.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/members/images/banners")
@Presentation
public class ProfileBannerApi {

	private final UpdateMemberUseCase updateMemberUseCase;
	private final DeleteMemberUseCase deleteMemberUseCase;

	@PutMapping
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @RequestPart MultipartFile profileBanner) {
		updateMemberUseCase.updateProfileBanner(MemberId.of(authInfo.getMemberId()), profileBanner);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping
	public ResponseEntity<Void> delete(@Auth AuthInfo authInfo){
		deleteMemberUseCase.deleteBanner(MemberId.of(authInfo.getMemberId()));
		return ResponseEntity.ok().build();
	}
}
