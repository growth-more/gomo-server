package com.gomo.app.core.interest.domain.model;

import java.util.UUID;

import com.gomo.app.common.arch.Authorizable;
import com.gomo.app.common.jpa.BaseAudit;
import com.gomo.app.core.interest.domain.exception.InterestAccessDeniedException;
import com.gomo.app.core.interest.domain.exception.code.InterestErrorCode;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Interest extends BaseAudit implements Authorizable {

	@Id
	private UUID id;
	private UUID registrantId;

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

	public Interest(UUID id, UUID registrantId, Proficiency proficiency, InterestName name, Logo logo, String colorCode) {
		this.id = id;
		this.registrantId = registrantId;
		this.proficiency = proficiency;
		this.name = name;
		this.logo = logo;
		this.colorCode = colorCode;
	}

	public static Interest of(UUID id, UUID registrantId, InterestName name, Logo logo, String colorCode) {
		return new Interest(id, registrantId, Proficiency.createDefault(), name, logo, colorCode);
	}

	public UUID registrantId() {
		return this.registrantId;
	}

	public String name() {
		return this.name.getInterestName();
	}

	public String logoUrl() {
		return this.logo.getUrl();
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

	public void adjustProficiency(int deltaTotalScore, ProficiencyCalculator proficiencyCalculator) {
		this.proficiency = this.proficiency.adjust(deltaTotalScore, proficiencyCalculator);
	}

	@Override
	public void validateAuthority(UUID accessorId) {
		if (!accessorId.equals(this.registrantId)) {
			throw new InterestAccessDeniedException(InterestErrorCode.ACCESS_DENIED);
		}
	}
}
