package com.timetable.event;

import com.timetable.outlook.OutlookConnector;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class OutlookEventManager {
    private final ExchangeService service;

    @Autowired
    public OutlookEventManager(OutlookConnector connector) {
        service = connector.getService();
    }

    public void createEvent(Event event) throws Exception {
        Appointment appointment = new Appointment(service);
        appointment.setSubject(event.getName());
        appointment.setStart(event.getStartDate());
        appointment.setEnd(event.getEndDate());
        appointment.setLocation(event.getLocation());
        //appointment.getRequiredAttendees().add(new Attendee("m.bhuiyan@innopolis.university"));
        appointment.save();
        //EmailAddress email = new EmailAddress("m.bhuiyan@innopolis.university");
        //appointment.forward(MessageBody.getMessageBodyFromText("Come to the meeting!"), email);
    }
}
