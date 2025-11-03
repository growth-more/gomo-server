package com.gomo.app.core.member.application.port.in;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.core.member.domain.exception.MemberNotFoundException;

public interface ProfileBannerUpdater {

	/**
	 * Updates the profile banner for a specific member by uploading a new image.
	 *
	 * @param memberId      The ID of the member whose profile banner is to be updated.
	 * @param profileBanner The new banner image file to be uploaded.
	 * @throws MemberNotFoundException if no member is found with the provided ID.
	 */
	void update(UUID memberId, MultipartFile profileBanner);
}
