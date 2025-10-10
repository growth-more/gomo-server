package com.gomo.app.common.jpa;

import java.time.LocalDateTime;

import jakarta.persistence.Column;

public abstract class LogicalDeleteBaseAudit extends BaseAudit {

	@Column(name = "deleted")
	protected LocalDateTime deletedAt;

	protected void delete() {
		deletedAt = LocalDateTime.now();
	}
}
