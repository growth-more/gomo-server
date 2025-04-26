package com.gomo.app.image;

import java.util.Set;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gomo.app.interest.domain.service.LogoService;
import com.gomo.app.member.domain.service.ProfileImageService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ImageCleanupScheduler {
    private final ImageService imageService;
    private final LogoService logoService;
    private final ProfileImageService profileImageService;

    @Scheduled(cron ="0 0 2 ? * SUN")
    public void cleanUnusedImages(){
        Set<String> allImages = imageService.readAllImages();

        Set<String> profileImages = profileImageService.getAllProfileImageUrl();
        allImages.removeAll(profileImages);

        Set<String> logoImages = logoService.getAllLogoUrl();
        allImages.removeAll(logoImages);

        for(String unusedImage : allImages){
            imageService.deleteImage(unusedImage);
        }
    }
}
