package com.gomo.app.common.domain.service;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.gomo.app.common.exception.DomainErrorCode;
import com.gomo.app.common.exception.PolicyViolationException;

@Component
public class OrderChanger {

	public static void change(List<OrderChangeable> candidates, List<DisplayOrder> changedOrders) {
		ensureCandidatesSize(candidates, changedOrders);
		IntStream.range(0, candidates.size()).forEach(i -> candidates.get(i).changeOrder(changedOrders.get(i)));
	}

	private static void ensureCandidatesSize(List<OrderChangeable> candidates, List<DisplayOrder> changedOrders) {
		if(candidates.size() != changedOrders.size()) {
			throw new PolicyViolationException(DomainErrorCode.INVALID_PARAMETER, "Candidates size and changed orders size must be the same");
		}
	}
}
