package com.gomo.app.core.point.domain.model;

import java.util.UUID;

import com.gomo.app.common.jpa.BaseAudit;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
public class PointWallet extends BaseAudit {

	@EmbeddedId
	private PointWalletId id;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "id", column = @Column(name = "transactor_id"))
	})
	private TransactorId transactorId;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "amount", column = @Column(name = "balance"))
	})
	private Balance balance;

	protected PointWallet() {
	}

	private PointWallet(PointWalletId id, TransactorId transactorId, Balance balance) {
		this.id = id;
		this.transactorId = transactorId;
		this.balance = balance;
	}

	public static PointWallet createDefault(PointWalletId id, TransactorId transactorId) {
		return new PointWallet(id, transactorId, Balance.of(0));
	}

	public static PointWallet of(PointWalletId id, TransactorId transactorId, Balance balance) {
		return new PointWallet(id, transactorId, balance);
	}

	public UUID id() {
		return this.id.getId();
	}

	public void adjustBalance(int deltaAmount) {
		this.balance = this.balance.update(deltaAmount);
	}
}
