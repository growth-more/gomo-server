package com.gomo.app.core.interest.domain.model;

import static com.gomo.app.core.interest.domain.exception.code.InterestRelationErrorCode.*;

import java.util.UUID;

import com.gomo.app.common.arch.Authorizable;
import com.gomo.app.common.jpa.BaseAudit;
import com.gomo.app.core.interest.domain.exception.InterestRelationAccessDeniedException;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class InterestRelation extends BaseAudit implements Authorizable {

	@Id
	private UUID id;
	private UUID registrantId;
	private UUID parentInterestId;
	private UUID childInterestId;

	protected InterestRelation() {
	}

	private InterestRelation(UUID id, UUID registrantId, UUID parentInterestId, UUID childInterestId) {
		this.id = id;
		this.registrantId = registrantId;
		this.parentInterestId = parentInterestId;
		this.childInterestId = childInterestId;
	}

	public static InterestRelation of(UUID id, UUID registrantId, UUID parentInterestId, UUID childInterestId) {
		return new InterestRelation(id, registrantId, parentInterestId, childInterestId);
	}

	@Override
	public void validateAuthority(UUID accessorId) {
		if (!accessorId.equals(registrantId)) {
			throw new InterestRelationAccessDeniedException(ACCESS_DENIED);
		}
	}
}
