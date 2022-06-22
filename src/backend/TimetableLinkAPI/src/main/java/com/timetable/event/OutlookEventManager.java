package com.timetable.event;

import com.timetable.mailing_list.MailingList;
import com.timetable.outlook.OutlookConnector;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.core.service.schema.ItemSchema;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.property.complex.EmailAddress;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.ItemView;
import microsoft.exchange.webservices.data.search.filter.SearchFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;


@Component
public class OutlookEventManager {
    private final ExchangeService service;

    @Autowired
    public OutlookEventManager(OutlookConnector connector) {
        service = connector.getService();
    }

    public Event getEvent(String encodedId) throws Exception {
        Appointment appointment = findAppointmentById(encodedId);

        return new Event(appointment.getSubject(),
                appointment.getStart(),
                appointment.getEnd(),
                appointment.getLocation(),
                encodedId);
    }

    public String createEvent(Event event) throws Exception {
        Appointment appointment = new Appointment(service);
        appointment.setSubject(event.getName());
        appointment.setStart(event.getStartDate());
        appointment.setEnd(event.getEndDate());
        appointment.setLocation(event.getLocation());
        //appointment.getRequiredAttendees().add(new Attendee("m.bhuiyan@innopolis.university"));
        appointment.save();
        String decodedId = appointment.getId().getUniqueId();
        return encodeBase64(decodedId);
    }

    public void inviteToEvent(String encodedId, List<String> emails) throws Exception {
        Appointment appointment = findAppointmentById(encodedId);
        List<EmailAddress> emailAddresses = new ArrayList<>();
        String textMessage = "You are invited to " + appointment.getSubject() +
                "\nYou can see the information about the meeting in the Calendar";
        for (String email : emails) {
            EmailAddress emailAddress = new EmailAddress(email);
            emailAddresses.add(emailAddress);
        }
        appointment.forward(MessageBody.getMessageBodyFromText(textMessage), emailAddresses);
    }

    public Appointment findAppointmentById(String encodedId) throws Exception {
        String decodedId = decodeBase64(encodedId);
        FindItemsResults<Item> appointmentSearch = service.findItems(
                WellKnownFolderName.Calendar,
                new SearchFilter.IsEqualTo(ItemSchema.Id, decodedId),
                new ItemView(1));

        return (Appointment)appointmentSearch.getItems().get(0);
    }

    public void updateEvent(String encodedId, Event event) throws Exception {
        Appointment appointment = findAppointmentById(encodedId);
        appointment.setSubject(event.getName());
        appointment.setStart(event.getStartDate());
        appointment.setEnd(event.getEndDate());
        appointment.setLocation(event.getLocation());
        appointment.save();
    }

    public void cancelEvent(String eventId) throws Exception {
        Appointment appointment = findAppointmentById(eventId);
        appointment.cancelMeeting();
        appointment.save();
    }

    private String decodeBase64(String encodedString) {
        byte[] decodedIdBytes = Base64.getDecoder().decode(encodedString);
        return new String(decodedIdBytes);
    }

    private String encodeBase64(String decodedString) {
        byte[] encodedIdBytes = Base64.getEncoder().encode(decodedString.getBytes());
        return new String(encodedIdBytes);
    }

//    private String formatDate(Date date) {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//        return dateFormat.format(date);
//    }
}
