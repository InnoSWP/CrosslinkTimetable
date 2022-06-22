package com.timetable.event;

import java.util.Date;


public class Event {
    private final String name;
    private final Date startDate;
    private final Date endDate;
    private final String location;
    private final String id;


    public Event(String name,
                 Date startDate,
                 Date endDate,
                 String location,
                 String id) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.id = id;
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

    public String getId() {
        return id;
    }
}
