package com.gomo.app.member.application;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.member.domain.model.Handle;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.exception.HandleDuplicatedException;
import com.gomo.app.member.exception.MemberNotFoundException;
import com.gomo.app.member.exception.code.HandleErrorCode;
import com.gomo.app.member.exception.code.MemberErrorCode;
import com.gomo.app.member.presentation.response.ReadMemberResponse;
import com.gomo.app.point.domain.model.Balance;
import com.gomo.app.point.domain.model.TransactorId;
import com.gomo.app.point.domain.service.PointWalletService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadMemberUseCase {

	private final MemberRepository memberRepository;
	private final PointWalletService pointWalletService;

	public ReadMemberResponse find(MemberId memberId) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new MemberNotFoundException(MemberErrorCode.NOT_FOUND));

		Balance balance = pointWalletService.findBalance(TransactorId.of(member.getId().getId()));
		return ReadMemberResponse.of(member, balance.getAmount());
	}

	public void checkDuplicate(String handle){
		if (memberRepository.existsByHandle(Handle.of(handle))){
			throw new HandleDuplicatedException(HandleErrorCode.DUPLICATED);
		}
	}


}
