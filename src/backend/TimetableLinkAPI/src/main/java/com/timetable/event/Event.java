package com.timetable.event;

import java.util.Date;


public class Event {
    private final Long id;
    private final String name;
    private final Date startDate;
    private final Date endDate;
    private final String location;


    public Event(Long id,
                 String name,
                 Date startDate,
                 Date endDate,
                 String location) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getLocation() {
        return location;
    }

}
