package com.gomo.app.member.application;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.interest.application.port.UploadLogoPortOut;
import com.gomo.app.interest.application.port.dto.LogoDto;
import com.gomo.app.logging.AuditLog;
import com.gomo.app.member.application.port.dto.UpdateProfileImageDto;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateProfileImageUseCase {

	private final MemberService memberService;
	private final UploadLogoPortOut uploadLogoPortOut;

	@AuditLog(action = "UPDATE_PROFILE_IMAGE")
	public UpdateProfileImageDto update(UUID memberId, MultipartFile profileImage) {
		Member member = memberService.find(MemberId.of(memberId));
		LogoDto logoDto = uploadLogoPortOut.upload(profileImage);
		member.updateProfileImage(logoDto.url());
		return UpdateProfileImageDto.of(logoDto.url());
	}
}
