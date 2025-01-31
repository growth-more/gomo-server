package com.gomo.app.interest.domain.model;

import static com.gomo.app.interest.exception.InterestRelationErrorCode.*;

import java.util.UUID;

import com.gomo.app.common.domain.Authorizable;
import com.gomo.app.common.domain.BaseAudit;
import com.gomo.app.interest.exception.InterestRelationAccessDeniedException;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
public class InterestRelation extends BaseAudit implements Authorizable {

	@EmbeddedId
	private InterestRelationId id;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "id", column = @Column(name = "registrant_id"))
	})
	private RegistrantId registrantId;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "id", column = @Column(name = "parent_interest_id"))
	})
	private ParentInterestId parentInterestId;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "id", column = @Column(name = "child_interest_id"))
	})
	private ChildInterestId childInterestId;

	protected InterestRelation() {}

	private InterestRelation(
		InterestRelationId id,
		RegistrantId registrantId,
		ParentInterestId parentInterestId,
		ChildInterestId childInterestId
	) {
		this.id = id;
		this.registrantId = registrantId;
		this.parentInterestId = parentInterestId;
		this.childInterestId = childInterestId;
	}

	public static InterestRelation of(
		InterestRelationId id,
		RegistrantId registrantId,
		ParentInterestId parentInterestId,
		ChildInterestId childInterestId
	) {
		return new InterestRelation(id, registrantId, parentInterestId, childInterestId);
	}

	@Override
	public void validateAuthority(UUID accessorId) {
		if(!accessorId.equals(registrantId.getId())) {
			throw new InterestRelationAccessDeniedException(ACCESS_DENIED);
		}
	}
}
