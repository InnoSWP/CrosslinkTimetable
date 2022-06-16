package com.timetable.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    private final OutlookEventManager eventManager;

    @Autowired
    public EventService(OutlookEventManager eventManager) {
        this.eventManager = eventManager;
    }

    public void createEvent(Event event) throws Exception {
        eventManager.createEvent(event);
    }
}
