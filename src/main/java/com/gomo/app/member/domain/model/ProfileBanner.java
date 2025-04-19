package com.gomo.app.member.domain.model;

import com.gomo.app.common.domain.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class ProfileBanner {

    private static final String DEFAULT_IMAGE = "DEFAULT_IMAGE";

    private String url;

    protected ProfileBanner() {
    }

    private ProfileBanner(String url){
        this.url = url;
    }

    public static ProfileBanner createDefault() {
        return new ProfileBanner(DEFAULT_IMAGE);
    }

    public ProfileBanner updateUrl(String url){return new ProfileBanner(url);}

    public ProfileBanner delete() {
        return new ProfileBanner(DEFAULT_IMAGE);
    }
}
