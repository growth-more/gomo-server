package com.gomo.app.member.domain.model;

import com.gomo.app.common.domain.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class ProfileImage {

    private static final String DEFAULT_IMAGE = "DEFAULT_IMAGE";

    private String url;

    protected ProfileImage() {
    }

    public ProfileImage(String url) {
        this.url = url;
    }

    public static ProfileImage createDefault() {
        return new ProfileImage(DEFAULT_IMAGE);
    }

    public ProfileImage updateUrl(String url) {
        return new ProfileImage(url);
    }

    public ProfileImage delete() {
        return new ProfileImage(DEFAULT_IMAGE);
    }
}
