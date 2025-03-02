package com.gomo.app.member.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.gomo.app.member.domain.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, MemberId> {

	boolean existsByEmail(Email email);
	boolean existsByHandle(Handle handle);

	Optional<Member> findByEmail(Email email);

	List<Member> findByActivateStatusAndLastLoginDateTimeGreaterThanEqual(
		ActivateStatus activateStatus,
		LocalDateTime loginDateTime
	);

	@Query("SELECT m.profileImage FROM Member m WHERE m.profileImage IS NOT NULL")
	List<String> findAllByProfileImageUrl();
}
