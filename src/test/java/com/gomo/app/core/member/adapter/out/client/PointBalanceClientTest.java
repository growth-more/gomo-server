package com.gomo.app.core.member.adapter.out.client;

import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.point.application.port.in.BalanceReader;

@DisplayName("[Adapter Unit]: 포인트 잔고 조회 테스트")
@ExtendWith(MockitoExtension.class)
class PointBalanceClientTest {

	@InjectMocks
	private PointBalanceClient sut;

	@Mock
	private BalanceReader balanceReader;

	@DisplayName("포인트 잔고를 조회한다.")
	@Test
	void read_point_balance() {
		sut.read(UUID.randomUUID());

		verify(balanceReader, times(1)).find(any());
	}
}
