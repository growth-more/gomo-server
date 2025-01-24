package com.gomo.app.member.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;

public interface MemberRepository extends JpaRepository<Member, MemberId> {
}
