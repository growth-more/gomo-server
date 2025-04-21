package com.gomo.app.interest.domain.model;

import java.util.Objects;

import com.gomo.app.common.domain.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class Logo {

    private static final String DEFAULT_IMAGE = "DEFAULT_IMAGE";

    private String url;

    protected Logo() {
    }

    private Logo(String url) {
        this.url = url;
    }

    public static Logo of(String url) {
        if(url == null) {
            return new Logo(DEFAULT_IMAGE);
        }
        return new Logo(url);
    }

    public boolean isDefault() {
        return DEFAULT_IMAGE.equals(this.url);
    }

    public Logo updateUrl(String url) {
        return new Logo(url);
    }

    public Logo delete() {
        return new Logo(DEFAULT_IMAGE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Logo logo = (Logo)o;
        return Objects.equals(url, logo.url);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(url);
    }
}
