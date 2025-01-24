package com.gomo.app.interest.domain.model;

import java.util.UUID;

import com.gomo.app.common.domain.Authorizable;
import com.gomo.app.common.domain.BaseAudit;

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
		return new Interest(id, registrantId, Proficiency.createDefault(), name, logoUrl);
	}

	public void updateName(InterestName name) {
	}

	public void updateLogoUrl(String logoUrl) {
	}

	public MajorInterest selectMajor() {
		return null;
	}

	public void enhanceProficiency(int increment, int scoreThreshold) {
	}

	@Override
	public void validateAuthority(UUID accessorId) {

	}
}
