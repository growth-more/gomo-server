package com.gomo.app.core.member.adapter.out.client;

import java.util.UUID;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.core.member.application.port.out.PointBalanceReader;
import com.gomo.app.core.point.application.port.ReadBalancePortIn;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class PointBalanceClient implements PointBalanceReader {

	private final ReadBalancePortIn readBalancePortIn;

	@Override
	public int read(UUID memberId) {
		return readBalancePortIn.find(memberId);
	}
}
