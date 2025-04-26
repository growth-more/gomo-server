package com.gomo.app.interest.domain.model;

import static com.gomo.app.interest.exception.code.MajorInterestErrorCode.*;

import java.util.UUID;

import com.gomo.app.common.Authorizable;
import com.gomo.app.common.BaseAudit;
import com.gomo.app.displayorder.DisplayOrder;
import com.gomo.app.displayorder.OrderChangeable;
import com.gomo.app.interest.exception.MajorInterestAccessDeniedException;

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

	@Override
	public void changeOrder(DisplayOrder displayOrder) {
		this.displayOrder = displayOrder;
	}

	@Override
	public void validateAuthority(UUID accessorId) {
		if(!accessorId.equals(this.registrantId.getId())) {
			throw new MajorInterestAccessDeniedException(ACCESS_DENIED);
		}
	}
}
