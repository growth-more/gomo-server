package com.gomo.app.core.interest.domain.model;

import static com.gomo.app.core.interest.exception.code.MajorInterestErrorCode.*;

import java.util.UUID;

import com.gomo.app.common.arch.Authorizable;
import com.gomo.app.common.displayorder.DisplayOrder;
import com.gomo.app.common.displayorder.OrderChangeable;
import com.gomo.app.common.jpa.BaseAudit;
import com.gomo.app.core.interest.exception.MajorInterestAccessDeniedException;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
public class MajorInterest extends BaseAudit implements OrderChangeable, Authorizable {

	@EmbeddedId
	private MajorInterestId id;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "id", column = @Column(name = "registrant_id"))
	})
	private RegistrantId registrantId;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "id", column = @Column(name = "interest_id"))
	})
	private InterestId interestId;

	@Embedded
	private DisplayOrder displayOrder;

	protected MajorInterest() {
	}

	private MajorInterest(
		MajorInterestId id,
		RegistrantId registrantId,
		InterestId interestId,
		DisplayOrder displayOrder
	) {
		this.id = id;
		this.registrantId = registrantId;
		this.interestId = interestId;
		this.displayOrder = displayOrder;
	}

	public static MajorInterest of(
		MajorInterestId id,
		RegistrantId registrantId,
		InterestId interestId,
		DisplayOrder displayOrder
	) {
		return new MajorInterest(id, registrantId, interestId, displayOrder);
	}

	public UUID uuid() {
		return this.id.getId();
	}

	public UUID interestUuid() {
		return this.interestId.getId();
	}

	public UUID registrantUuid() {
		return this.registrantId.getId();
	}

	public int displayOrder() {
		return this.displayOrder.getDisplayOrder();
	}

	@Override
	public void changeOrder(DisplayOrder displayOrder) {
		this.displayOrder = displayOrder;
	}

	@Override
	public void validateAuthority(UUID accessorId) {
		if (!accessorId.equals(this.registrantId.getId())) {
			throw new MajorInterestAccessDeniedException(ACCESS_DENIED);
		}
	}
}
