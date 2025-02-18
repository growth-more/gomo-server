package com.gomo.app.point.presentation;

import com.gomo.app.common.authentication.Auth;
import com.gomo.app.common.authentication.AuthInfo;
import com.gomo.app.common.dto.PageRequest;
import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.point.application.ReadBalanceUseCase;
import com.gomo.app.point.application.ReadPointUseCase;
import com.gomo.app.point.domain.model.TransactorId;
import com.gomo.app.point.presentation.response.ListPointResponse;
import com.gomo.app.point.presentation.response.ReadBalanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/points")
@Presentation
public class PointApi {

	private final ReadPointUseCase readPointUseCase;
	private final ReadBalanceUseCase readBalanceUseCase;

	@GetMapping
	public ResponseEntity<ListPointResponse> findAll(@Auth AuthInfo authInfo, @ModelAttribute PageRequest pageRequest) {
		ListPointResponse response = readPointUseCase.findAll(TransactorId.of(authInfo.getMemberId()), pageRequest);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/balances")
	public ResponseEntity<ReadBalanceResponse> findAll(@Auth AuthInfo authInfo) {
		ReadBalanceResponse response = readBalanceUseCase.find(TransactorId.of(authInfo.getMemberId()));
		return ResponseEntity.ok(response);
	}
}
