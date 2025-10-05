package com.gomo.app.support.event.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gomo.app.support.event.domain.model.EventEntry;

public interface EventEntryRepository extends JpaRepository<EventEntry, Long> {

	@Query(value = "select * from event_entry e" +
		" where e.event_status = :eventStatus" +
		" limit :size",
		nativeQuery = true)
	List<EventEntry> findByEventStatus(@Param("eventStatus") String eventStatus, @Param("size") int size);
}
