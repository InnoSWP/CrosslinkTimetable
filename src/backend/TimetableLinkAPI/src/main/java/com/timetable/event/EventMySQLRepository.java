package com.timetable.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventMySQLRepository implements EventRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EventMySQLRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveEvent(String eventId) {
        jdbcTemplate.update(
                "INSERT INTO event (outlookAppointmentId) values (?)", eventId);
    }

    @Override
    public void deleteEvent(String eventId) {
        jdbcTemplate.update(
                "DELETE FROM event WHERE outlookAppointmentId = ?", eventId);
    }

    @Override
    public List<String> getAllIds() {
        return jdbcTemplate.queryForList(
                "SELECT outlookAppointmentId FROM event", String.class);
    }


}
