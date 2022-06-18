package com.timetable.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        try {
            eventService.createEvent(event);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @PutMapping ("/{eventId}/{mailingListTextIdentifier}")
    public void inviteAllFromMailingList(@PathVariable Long eventId,
                                         @PathVariable String mailingListTextIdentifier) {

    }

    @PutMapping("/{eventId}/cancelInvitation/{mailingListTextIdentifier}")
    public void cancelInvitationForEveryoneFromList(
            @PathVariable Long eventId,
            @PathVariable String mailingListTextIdentifier) {

    }
}
