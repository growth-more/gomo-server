package com.gomo.app.point.common.dataprovider;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gomo.app.point.domain.model.PointWallet;
import com.gomo.app.point.domain.model.PointWalletId;
import com.gomo.app.point.domain.repository.PointWalletRepository;

import jakarta.annotation.PostConstruct;

/**
 * 실제 데이터베이스에 존재하는 포인트 지갑 데이터를 제공한다.
 * @ amount: 1660
 */
@Component
public class PointWalletDataProvider {

	private static final String POINT_WALLET_ID = "e23db9d3-e6e5-11ef-9f07-0b157ee08b8d";
	private PointWallet pointWallet;

	@Autowired
	private PointWalletRepository pointWalletRepository;

	@PostConstruct
	public void initialize() {
		pointWallet = pointWalletRepository.findById(PointWalletId.of(UUID.fromString(POINT_WALLET_ID)))
			.orElseThrow(() -> new IllegalStateException("PointWalletDataProvider 초기화 실패: POINT_WALLET_ID에 해당하는 PointWallet이 없습니다."));
	}

	public PointWallet pointWallet() {
		return pointWallet;
	}
}
