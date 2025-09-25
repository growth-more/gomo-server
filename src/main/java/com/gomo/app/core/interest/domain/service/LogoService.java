package com.gomo.app.core.interest.domain.service;

import com.gomo.app.common.arch.DomainService;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@DomainService
public class LogoService {
    private final InterestRepository interestRepository;

    public Set<String> getAllLogoUrl(){
        return new HashSet<>(interestRepository.findAllLogoUrl());
    }
}
