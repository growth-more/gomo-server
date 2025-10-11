package com.gomo.app.core.interest.domain.model;

import static com.gomo.app.core.interest.exception.code.MajorInterestErrorCode.*;

import java.util.UUID;

import com.gomo.app.common.arch.Authorizable;
import com.gomo.app.common.displayorder.DisplayOrder;
import com.gomo.app.common.displayorder.OrderChangeable;
import com.gomo.app.common.jpa.BaseAudit;
import com.gomo.app.core.interest.exception.MajorInterestAccessDeniedException;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class MajorInterest extends BaseAudit implements OrderChangeable, Authorizable {

	@Id
	private UUID id;
	private UUID registrantId;
	private UUID interestId;

	@Embedded
	private DisplayOrder displayOrder;

	protected MajorInterest() {
	}

	private MajorInterest(UUID id, UUID registrantId, UUID interestId, DisplayOrder displayOrder) {
		this.id = id;
		this.registrantId = registrantId;
		this.interestId = interestId;
		this.displayOrder = displayOrder;
	}

	public static MajorInterest of(UUID id, UUID registrantId, UUID interestId, DisplayOrder displayOrder) {
		return new MajorInterest(id, registrantId, interestId, displayOrder);
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
		if (!accessorId.equals(this.registrantId)) {
			throw new MajorInterestAccessDeniedException(ACCESS_DENIED);
		}
	}
}
