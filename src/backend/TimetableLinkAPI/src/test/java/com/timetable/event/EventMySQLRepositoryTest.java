package com.timetable.event;

import com.timetable.mailing_list.MailingList;
import com.timetable.mailing_list.MailingListMySQLRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@Sql({"/h2_schema.sql"})
class EventMySQLRepositoryTest {
    JdbcTemplate jdbcTemplate;
    EventMySQLRepository repo;

    @Autowired
    EventMySQLRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.repo = new EventMySQLRepository(jdbcTemplate);
    }

    @Test
    void operateEvents() {
        repo.saveEvent("1");
        assertEquals(repo.getAllIds(), List.of("1"));
        repo.deleteEvent("1");
        assertEquals(repo.getAllIds(), List.of());
    }
}