package com.timetable.event;

import org.apache.tomcat.util.buf.UDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping(path = "/events")
public class EventController {
    EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public Map<String, String> createEvent(@RequestBody Event event) {
        //System.out.println(event.getEndDate());
        Map<String, String> eventIdMap = new HashMap<>();
        try {
            String eventId = eventService.createEvent(event);
            eventIdMap.put("eventId", eventId);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return eventIdMap;
    }

    @GetMapping ("/{eventId}")
    public Event getEvent(@PathVariable String eventId) {
        Event event = null;
        try {
            event = eventService.getEvent(eventId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return event;
    }

    @PutMapping("/{eventId}")
    public void updateEvent(@PathVariable String eventId, @RequestBody Event event) {
        try {
            eventService.updateEvent(eventId, event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DeleteMapping("/{eventId}")
    public void cancelEvent(@PathVariable String eventId) {
        try {
            eventService.cancelEvent(eventId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PatchMapping("/{eventId}/invite")
    public void inviteAllFromMailingLists(
            @PathVariable String eventId,
            @RequestBody List<String> mailingListsTextIdentifiers) {
        try {
            eventService.inviteToEvent(eventId, mailingListsTextIdentifiers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //parameters "start" and "end" are in query
    @GetMapping("/eventsFromTimeInterval")
    public List<Event> getEventsFromTimeInterval(@RequestParam Date start,
                                                 @RequestParam Date end) {
        List<Event> events = null;
        try {
            events = eventService.getEventsFromTimeInterval(start, end);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

    @GetMapping()
    public List<Event> getAllEvents() {
        List<Event> events = null;
        try {
            events = eventService.getAllEvents();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

    @GetMapping("/names")
    public List<String> getAllNames() {
        List<String> names = null;
        try {
            names = getAllEvents().stream().map(Event::getName).toList();
        } catch (Exception ex) {}
        return names;
    }

    @PatchMapping ("/{eventId}/invite/{mailingListTextIdentifier}")
    public void inviteAllFromMailingList(@PathVariable String eventId,
                                         @PathVariable String mailingListTextIdentifier) {
        try {
            eventService.inviteToEvent(eventId, mailingListTextIdentifier);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PatchMapping("/{eventId}/cancelInvitation/{mailingListTextIdentifier}")
    public void cancelInvitationForEveryoneFromList(
            @PathVariable String eventId,
            @PathVariable String mailingListTextIdentifier) {
        try {
            eventService.cancelInvitations(eventId, mailingListTextIdentifier);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
