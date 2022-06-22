package com.timetable.event;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository {
    void saveEvent(String eventId);
    void deleteEvent(String eventId);
    List<String> getAllIds();
}
