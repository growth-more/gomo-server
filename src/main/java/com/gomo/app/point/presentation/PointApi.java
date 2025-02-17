package com.gomo.app.point.presentation;

import com.gomo.app.common.authentication.Auth;
import com.gomo.app.member.domain.model.MemberId;
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
	public ResponseEntity<ListPointResponse> findAll(@Auth MemberId memberId, @ModelAttribute PageRequest pageRequest) {
		ListPointResponse response = readPointUseCase.findAll(TransactorId.of(memberId.getId()), pageRequest);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/balances")
	public ResponseEntity<ReadBalanceResponse> findAll(@Auth MemberId memberId) {
		ReadBalanceResponse response = readBalanceUseCase.find(TransactorId.of(memberId.getId()));
		return ResponseEntity.ok(response);
	}
}
