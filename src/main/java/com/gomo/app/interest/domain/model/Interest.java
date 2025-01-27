package com.gomo.app.interest.domain.model;

import java.util.UUID;

import com.gomo.app.common.domain.Authorizable;
import com.gomo.app.common.domain.BaseAudit;
import com.gomo.app.interest.exception.InterestAccessDeniedException;
import com.gomo.app.interest.exception.InterestErrorCode;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
public class Interest extends BaseAudit implements Authorizable {

	private static final String DEFAULT_LOGO_URL = "https://image.nurdykim.me/gomo/default-logo.png";

	@EmbeddedId
	private InterestId id;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "id", column = @Column(name = "registrant_id"))
	})
	private RegistrantId registrantId;

	@Embedded
	private Proficiency proficiency;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "interestName", column = @Column(name = "name"))
	})
	private InterestName name;
	private String logoUrl;

	protected Interest() {
	}

	public Interest(
		InterestId id,
		RegistrantId registrantId,
		Proficiency proficiency,
		InterestName name,
		String logoUrl
	) {
		this.id = id;
		this.registrantId = registrantId;
		this.proficiency = proficiency;
		this.name = name;
		this.logoUrl = logoUrl;
	}

	public static Interest of(
		InterestId id,
		RegistrantId registrantId,
		InterestName name,
		String logoUrl
	) {
		if(logoUrl == null) {
			logoUrl = DEFAULT_LOGO_URL;
		}
		return new Interest(id, registrantId, Proficiency.createDefault(), name, logoUrl);
	}

	public void updateName(InterestName updatedName) {
		this.name = updatedName;
	}

	public void updateLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public MajorInterest selectMajor() {
		return null;
	}

	public void enhanceProficiency(int increment, int scoreThreshold) {
		Proficiency enhancedProficiency = this.proficiency.enhance(increment, scoreThreshold);
		this.proficiency = enhancedProficiency;
	}

	@Override
	public void validateAuthority(UUID accessorId) {
		if(!accessorId.equals(registrantId.getId())) {
			throw new InterestAccessDeniedException(InterestErrorCode.ACCESS_DENIED);
		}
	}
}
