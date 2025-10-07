package com.gomo.app.core.point.application.port;

import java.util.UUID;

import com.gomo.app.core.point.domain.model.SourceType;
import com.gomo.app.core.point.domain.model.TransactionType;

public interface CreatePointPortIn {

	/**
	 * Creates a new point transaction.
	 *
	 * @param transactorId    The id of the user involved in the transaction.
	 * @param sourceType      The origin of the transaction. Must match a name from the {@link SourceType} enum (e.g., "QUEST", "STORE").
	 * @param transactionType The type of transaction. Must match a name from the {@link TransactionType} enum (e.g., "GAIN", "SPEND").
	 * @param amount          The number of points for the transaction. This should always be a positive value,
	 *                        as the operation (addition or subtraction) is determined by the {@code transactionType}.
	 */
	void create(UUID transactorId, String sourceType, String transactionType, int amount);
}
