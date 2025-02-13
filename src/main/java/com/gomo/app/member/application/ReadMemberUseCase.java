package com.gomo.app.member.application;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.Handle;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.exception.MemberDuplicatedException;
import com.gomo.app.member.exception.MemberErrorCode;
import com.gomo.app.member.exception.MemberNotFoundException;
import com.gomo.app.member.presentation.response.ReadMemberResponse;
import com.gomo.app.point.domain.model.TransactorId;
import com.gomo.app.point.domain.service.PointService;

import com.gomo.app.point.domain.service.PointWalletService;
import lombok.RequiredArgsConstructor;

import static com.gomo.app.member.exception.MemberErrorCode.HANDLE_DUPLICATED;

@RequiredArgsConstructor
@ApplicationService
public class ReadMemberUseCase {

	private final MemberRepository memberRepository;

	public ReadMemberResponse find(MemberId memberId) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new MemberNotFoundException(MemberErrorCode.NOT_FOUND, "member has not found"));

		// todo: Point 구현 확인하고 담아서 보내기
		return ReadMemberResponse.of(member, 1);
	}

	public void checkDuplicate(String handle){
		if (memberRepository.existsByHandle(Handle.of(handle))){
			throw new MemberDuplicatedException(HANDLE_DUPLICATED, "Handle already exists");
		}
	}


}
