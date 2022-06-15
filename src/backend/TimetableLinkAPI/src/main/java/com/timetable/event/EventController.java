package com.timetable.event;

import com.timetable.outlook.EventManager;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RestController
@RequestMapping(path = "/events")
public class EventController {
    EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public void createEvent(@RequestBody Event event) {
        try{
            eventService.createEvent(event);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
