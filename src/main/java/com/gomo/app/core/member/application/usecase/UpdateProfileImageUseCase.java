package com.gomo.app.core.member.application.usecase;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.member.application.port.dto.UpdateProfileImageDto;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.support.image.application.port.UploadImagePortIn;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateProfileImageUseCase {

	private final UploadImagePortIn uploadImagePortIn;
	private final MemberService memberService;

	@AuditLog(action = "UPDATE_PROFILE_IMAGE")
	public UpdateProfileImageDto update(UUID memberId, MultipartFile profileImage) {
		Member member = memberService.find(memberId);
		String profileUrl = uploadImagePortIn.upload(profileImage).orElse(null);
		;
		member.updateProfileImage(profileUrl);
		return UpdateProfileImageDto.of(profileUrl);
	}
}
