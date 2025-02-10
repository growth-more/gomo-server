package com.gomo.app.point.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.authentication.MemberContext;
import com.gomo.app.common.authentication.SessionMember;
import com.gomo.app.common.dto.PageRequest;
import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.point.application.ReadBalanceUseCase;
import com.gomo.app.point.application.ReadPointUseCase;
import com.gomo.app.point.domain.model.TransactorId;
import com.gomo.app.point.presentation.response.ListPointResponse;
import com.gomo.app.point.presentation.response.ReadBalanceResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/points")
@Presentation
public class PointApi {

	private final ReadPointUseCase readPointUseCase;
	private final ReadBalanceUseCase readBalanceUseCase;

	@GetMapping
	public ResponseEntity<ListPointResponse> findAll(@ModelAttribute PageRequest pageRequest) {
		SessionMember sessionMember = MemberContext.getSessionMember();
		ListPointResponse response = readPointUseCase.findAll(TransactorId.of(sessionMember.getId()), pageRequest);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/balances")
	public ResponseEntity<ReadBalanceResponse> findAll() {
		SessionMember sessionMember = MemberContext.getSessionMember();
		ReadBalanceResponse response = readBalanceUseCase.find(TransactorId.of(sessionMember.getId()));
		return ResponseEntity.ok(response);
	}
}
