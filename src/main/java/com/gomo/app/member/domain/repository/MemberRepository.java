package com.gomo.app.member.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gomo.app.member.domain.model.ActivateStatus;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;

public interface MemberRepository extends JpaRepository<Member, MemberId> {

	List<Member> findByActivateStatusAndLastLoginDateTimeGreaterThanEqual(
		ActivateStatus activateStatus,
		LocalDateTime loginDateTime
	);
}
