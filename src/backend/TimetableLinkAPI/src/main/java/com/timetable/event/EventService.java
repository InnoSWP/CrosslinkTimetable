package com.timetable.event;

import com.timetable.outlook.EventManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    private final EventManager eventManager;

    @Autowired
    public EventService(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public void createEvent(Event event) throws Exception {
        eventManager.createEvent(event);
    }
}
