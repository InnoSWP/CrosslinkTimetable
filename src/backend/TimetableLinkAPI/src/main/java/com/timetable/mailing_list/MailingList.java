//package com.timetable.mailing_list;
//
//import microsoft.exchange.webservices.data.property.complex.EmailAddress;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MailingList {
//    private Long id;
//    private Long textIdentifier;
//    private List<EmailAddress> emails;
//
//    public MailingList() {
//        emails = new ArrayList<>();
//    }
//
//    public MailingList(List<EmailAddress> emails) {
//        this.emails = emails;
//    }
//
//
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
//}
