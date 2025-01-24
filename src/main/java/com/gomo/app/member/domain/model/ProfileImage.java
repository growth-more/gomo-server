package com.gomo.app.member.domain.model;

import com.gomo.app.common.domain.ValueObject;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class ProfileImage {

    private static final String DEFAULT_IMAGE_URL = "https://mini-cloud/default-profile-image.png";
    private static final String DEFAULT_IMAGE_NAME = "default-profile-image.png";

    private String url;
    private String originName;

    protected ProfileImage() {
    }

    public ProfileImage(
        String url,
        String originName
    ) {
        this.url = url;
        this.originName = originName;
    }

    public static ProfileImage createDefault() {
        return new ProfileImage(DEFAULT_IMAGE_URL, DEFAULT_IMAGE_NAME);
    }

    public ProfileImage updateUrl(
        String url,
        String originName
    ) {
        return new ProfileImage(url, originName);
    }
}
