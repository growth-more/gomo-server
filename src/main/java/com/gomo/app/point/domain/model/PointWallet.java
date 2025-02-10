package com.gomo.app.point.domain.model;

import com.gomo.app.common.domain.BaseAudit;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Version;
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

	@Version
	private Long version;

	protected PointWallet() {}

	private PointWallet(
		PointWalletId id,
		TransactorId transactorId,
		Balance balance
	) {
		this.id = id;
		this.transactorId = transactorId;
		this.balance = balance;
	}

	public static PointWallet createDefault(PointWalletId id, TransactorId transactorId) {
		return new PointWallet(id, transactorId, Balance.of(0));
	}

	public static PointWallet of(
		PointWalletId id,
		TransactorId transactorId,
		Balance balance
	) {
		return new PointWallet(id, transactorId, balance);
	}

	public void adjustBalance(int deltaAmount) {
		this.balance = this.balance.update(deltaAmount);
	}
}
