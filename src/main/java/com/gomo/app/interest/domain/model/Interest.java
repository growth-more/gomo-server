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

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "url", column = @Column(name = "logo_url")),
	})
	private Logo logo;


	private String colorCode;

	protected Interest() {
	}

	public Interest(
		InterestId id,
		RegistrantId registrantId,
		Proficiency proficiency,
		InterestName name,
		Logo logo,
		String colorCode
	) {
		this.id = id;
		this.registrantId = registrantId;
		this.proficiency = proficiency;
		this.name = name;
		this.logo = logo;
		this.colorCode = colorCode;
	}

	public static Interest of(
		InterestId id,
		RegistrantId registrantId,
		InterestName name,
		Logo logo,
		String colorCode
	) {
		return new Interest(id, registrantId, Proficiency.createDefault(), name, logo, colorCode);
	}

	public void updateName(InterestName updatedName) {
		this.name = updatedName;
	}

	public void updateLogo(Logo logo) {
		this.logo = logo;
	}

	public void updateColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	public boolean hasDefaultLogo() {
		return this.logo.isDefault();
	}

	public void adjustProficiency(int deltaTotalScore, int[] totalScoreForLevel, int[] scoreThresholdsPerLevel) {
		this.proficiency = this.proficiency.adjust(deltaTotalScore, totalScoreForLevel, scoreThresholdsPerLevel);
	}

	@Override
	public void validateAuthority(UUID accessorId) {
		if(!accessorId.equals(this.registrantId.getId())) {
			throw new InterestAccessDeniedException(InterestErrorCode.ACCESS_DENIED);
		}
	}
}
