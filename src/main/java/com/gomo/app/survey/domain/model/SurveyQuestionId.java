package com.gomo.app.survey.domain.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import com.gomo.app.common.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class SurveyQuestionId implements Serializable {

    private UUID id;

    protected SurveyQuestionId() {
    }

    private SurveyQuestionId(UUID id) {
        this.id = id;
    }

    public static SurveyQuestionId of(UUID id) {
        return new SurveyQuestionId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SurveyQuestionId surveyQuestionId = (SurveyQuestionId)o;
        return Objects.equals(id, surveyQuestionId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
