package com.gomo.app.core.point.domain.model;

import java.util.UUID;

import com.gomo.app.common.jpa.BaseAudit;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class PointWallet extends BaseAudit {

	@Id
	private UUID id;
	private UUID transactorId;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "amount", column = @Column(name = "balance"))
	})
	private Balance balance;

	protected PointWallet() {
	}

	private PointWallet(UUID id, UUID transactorId, Balance balance) {
		this.id = id;
		this.transactorId = transactorId;
		this.balance = balance;
	}

	public static PointWallet createDefault(UUID id, UUID transactorId) {
		return new PointWallet(id, transactorId, Balance.of(0));
	}

	public static PointWallet of(UUID id, UUID transactorId, Balance balance) {
		return new PointWallet(id, transactorId, balance);
	}

	public void adjustBalance(int deltaAmount) {
		this.balance = this.balance.update(deltaAmount);
	}
}
