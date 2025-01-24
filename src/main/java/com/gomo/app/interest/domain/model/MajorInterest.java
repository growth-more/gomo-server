package com.gomo.app.interest.domain.model;

import java.util.UUID;

import com.gomo.app.common.domain.Authorizable;
import com.gomo.app.common.domain.BaseAudit;
import com.gomo.app.common.domain.DisplayOrder;
import com.gomo.app.common.domain.OrderChangeable;

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
		@AttributeOverride(name = "id", column = @Column(name = "member_id"))
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

	}

	@Override
	public void validateAuthority(UUID accessorId) {

	}
}
