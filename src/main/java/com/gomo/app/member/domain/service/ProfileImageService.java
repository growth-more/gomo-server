package com.gomo.app.member.domain.service;

import com.gomo.app.common.domain.service.DomainService;
import com.gomo.app.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@DomainService
@RequiredArgsConstructor
public class ProfileImageService {
    private final MemberRepository memberRepository;

    public Set<String> getAllProfileImageUrl(){
        return new HashSet<>(memberRepository.findAllByProfileImageUrl());
    }
}
