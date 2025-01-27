package com.gomo.app.interest.application;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.repository.InterestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateInterestLogoUseCase {

	// TODO <jhl221123>: 이미지 처리 기능 구현 후 수정 필요
	private final InterestRepository interestRepository;

	public void update(InterestId interestId, MultipartFile updatedLogo) {

	}
}
