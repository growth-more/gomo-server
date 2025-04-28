package com.gomo.app.member.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gomo.app.member.domain.model.ActivateStatus;
import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.Handle;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;

public interface MemberRepository extends JpaRepository<Member, MemberId> {

	Optional<Member> findByEmail(Email email);

	Optional<Member> findByHandle(Handle handle);

	List<Member> findByActivateStatusAndLastLoginDateTimeGreaterThanEqual(
		ActivateStatus activateStatus,
		LocalDateTime loginDateTime
	);

	@Query("SELECT m.profileImage FROM Member m WHERE m.profileImage IS NOT NULL")
	List<String> findAllByProfileImageUrl();
}
