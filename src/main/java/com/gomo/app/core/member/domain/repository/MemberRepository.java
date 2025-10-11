package com.gomo.app.core.member.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gomo.app.core.member.domain.model.ActivateStatus;
import com.gomo.app.core.member.domain.model.Email;
import com.gomo.app.core.member.domain.model.Handle;
import com.gomo.app.core.member.domain.model.Member;

public interface MemberRepository extends JpaRepository<Member, UUID> {

	Optional<Member> findByEmail(Email email);

	Optional<Member> findByHandle(Handle handle);

	List<Member> findByActivateStatusAndLastLoginDateTimeGreaterThanEqual(
		ActivateStatus activateStatus,
		LocalDateTime loginDateTime
	);

	List<Member> findByDeletedAtBefore(LocalDateTime date);

	@Query("SELECT m.profileImage FROM Member m WHERE m.profileImage IS NOT NULL")
	List<String> findAllByProfileImageUrl();
}
