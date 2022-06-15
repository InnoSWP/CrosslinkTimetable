package com.timetable.outlook;

import com.timetable.event.Event;
import microsoft.exchange.webservices.data.autodiscover.IAutodiscoverRedirectionUrl;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.EmailAddress;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@PropertySource("classpath:personal.properties")
public class EventManager {
    private ExchangeService service;
    private ExchangeCredentials credentials;

    public EventManager(
            @Value("${personal.email}") String personalEmail,
            @Value("${personal.password}") String personalPassword) {
        service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
        ExchangeCredentials credentials = new WebCredentials(personalEmail, personalPassword);
        service.setCredentials(credentials);
        try {
            service.autodiscoverUrl(
                    personalEmail, new RedirectionUrlCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createEvent(Event event) throws Exception {
        Appointment appointment = new Appointment(service);
        appointment.setSubject(event.getName());
        appointment.setStart(event.getStartDate());
        appointment.setEnd(event.getEndDate());
        appointment.setLocation(event.getLocation());
        appointment.save();
        EmailAddress email = new EmailAddress("i.kornienko@innopolis.university");
        appointment.forward(MessageBody.getMessageBodyFromText("Come to the meeting!"), email);
    }

    private static class RedirectionUrlCallback implements IAutodiscoverRedirectionUrl {
        public boolean autodiscoverRedirectionUrlValidationCallback(
                String redirectionUrl) {
            return redirectionUrl.toLowerCase().startsWith("https://");
        }
    }
}
