package com.gomo.app.event;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventEntryRepository extends JpaRepository<EventEntry, Long> {
}
