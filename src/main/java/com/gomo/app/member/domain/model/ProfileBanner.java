package com.gomo.app.member.domain.model;

import com.gomo.app.common.domain.ValueObject;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class ProfileBanner {
    private static final String DEFAULT_BANNER_URL = "https://image.nurdykim.me/gomo/default-banner.png";

    private String url;

    protected ProfileBanner() {
    }

    public ProfileBanner(String url){
        this.url = url;
    }

    public static ProfileBanner createDefault() {
        return new ProfileBanner(DEFAULT_BANNER_URL);
    }

    public ProfileBanner updateUrl(String url){return new ProfileBanner(url);}
    public ProfileBanner delete() {
        return new ProfileBanner(DEFAULT_BANNER_URL);
    }
}
