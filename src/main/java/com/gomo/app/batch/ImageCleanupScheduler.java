package com.gomo.app.batch;

import java.util.Set;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gomo.app.core.interest.domain.service.LogoService;
import com.gomo.app.core.member.domain.service.ProfileImageService;
import com.gomo.app.support.image.port.DeleteImagePortIn;
import com.gomo.app.support.image.port.ReadImagePortIn;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ImageCleanupScheduler {
	private final ReadImagePortIn readImagePortIn;
	private final DeleteImagePortIn deleteImagePortIn;
	private final LogoService logoService;
	private final ProfileImageService profileImageService;

	@Scheduled(cron = "0 0 2 ? * SUN")
	public void cleanUnusedImages() {
		Set<String> allImages = readImagePortIn.readAllImages();

		Set<String> profileImages = profileImageService.getAllProfileImageUrl();
		allImages.removeAll(profileImages);

		Set<String> logoImages = logoService.getAllLogoUrl();
		allImages.removeAll(logoImages);

		for (String unusedImage : allImages) {
			deleteImagePortIn.delete(unusedImage);
		}
	}
}
