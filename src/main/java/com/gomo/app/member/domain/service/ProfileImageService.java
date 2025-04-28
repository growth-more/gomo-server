package com.gomo.app.member.domain.service;

import java.util.HashSet;
import java.util.Set;

import com.gomo.app.common.DomainService;
import com.gomo.app.member.domain.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class ProfileImageService {

    private final MemberRepository memberRepository;

    public Set<String> getAllProfileImageUrl(){
        return new HashSet<>(memberRepository.findAllByProfileImageUrl());
    }
}
