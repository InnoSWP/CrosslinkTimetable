package com.timetable.event;

import com.timetable.mailing_list.MailingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EventService {
    private final OutlookEventManager eventManager;
    private final EventRepository eventRepository;
    private final MailingListService mailingListService;

    @Autowired
    public EventService(OutlookEventManager eventManager,
                        EventRepository eventRepository,
                        MailingListService mailingListService) {
        this.eventManager = eventManager;
        this.eventRepository = eventRepository;
        this.mailingListService = mailingListService;
    }

    public Event getEvent(String eventId) throws Exception {
        return eventManager.getEvent(eventId);
    }

    public List<Event> getAllEvents() throws Exception {
        List<String> eventIds = eventRepository.getAllIds();
        List<Event> events = new ArrayList<>();
        for (String id : eventIds) {
            events.add(getEvent(id));
        }
        return events;
    }

    public List<Event> getEventsFromTimeInterval(Date start, Date end) throws Exception {
        List<Event> filteredEvents = new ArrayList<>();
        for (Event event : getAllEvents()) {
            if (event.getStartDate().after(start) && event.getEndDate().before(end)) {
                filteredEvents.add(event);
            }
        }
        return filteredEvents;
    }

    public void updateEvent(String eventId, Event event) throws Exception {
        eventManager.updateEvent(eventId, event);
    }

    public String createEvent(Event event) throws Exception {
        String eventId = eventManager.createEvent(event);
        eventRepository.saveEvent(eventId);
        return eventId;
    }

    public void inviteToEvent(String eventId, List<String> mailingListsTextIdentifiers)
            throws Exception {
        for (String mailingListTextIdentifier : mailingListsTextIdentifiers) {
            inviteToEvent(eventId, mailingListTextIdentifier);
        }
    }

    public void inviteToEvent(String eventId, String mailingListTextIdentifier)
            throws Exception {
        List<String> emails = mailingListService.getEmailsFromList(mailingListTextIdentifier);
        eventManager.inviteToEvent(eventId, emails);
    }

    public void cancelInvitations(String eventId,
                                  String mailingListTextIdentifier) throws Exception {
        mailingListService.cancelInvitations(eventId, mailingListTextIdentifier);
    }

    public void cancelEvent(String eventId) throws Exception {
        eventRepository.deleteEvent(eventId);
        eventManager.cancelEvent(eventId);
    }
}
