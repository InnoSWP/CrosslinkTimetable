package com.timetable.mailing_list;

import microsoft.exchange.webservices.data.property.complex.EmailAddress;

import java.util.ArrayList;
import java.util.List;

public class MailingList {
    private final String textIdentifier;
    private final List<String> emails;

    public MailingList(String textIdentifier, List<String> emails) {
        this.textIdentifier = textIdentifier;
        this.emails = emails;
    }

    public String getTextIdentifier() {
        return textIdentifier;
    }

    public List<String> getEmails() {
        return emails;
    }


//    public List<EmailAddress> getEmails() {
//        return emails;
//    }
//
//    public void setEmails(List<EmailAddress> emails) {
//        this.emails = emails;
//    }
//
//    public void addEvent(EmailAddress email) {
//        emails.add(email);
//    }
//
//    public void excludeEmail(EmailAddress email) {
//        emails.remove(email);
//    }
}
