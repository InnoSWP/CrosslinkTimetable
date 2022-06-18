package com.timetable.mailing_list;

import microsoft.exchange.webservices.data.core.service.item.Contact;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.core.service.schema.ServiceObjectSchema;
import microsoft.exchange.webservices.data.property.complex.EmailAddress;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MailingListService {
    private final MailingListRepository mailingListRepository;
    private final OutlookMailingListManager mailingListManager;

    @Autowired
    public MailingListService(
            MailingListRepository mailingListRepository,
            OutlookMailingListManager mailingListManager) {
        this.mailingListRepository = mailingListRepository;
        this.mailingListManager = mailingListManager;
    }

    public List<String> getMailingListsNames() {
        return mailingListRepository.getMailingListsNames();
    }

    public void createMailingList(MailingList mailingList) {
//        List<EmailAddress> emailAddressesList = new ArrayList<>();
//        emails.forEach(email ->
//                emailAddressesList.add(new EmailAddress(email)));
//        MailingList mailingList = new MailingList(textIdentifier, emailAddressesList);
        mailingListRepository.createMailingList(mailingList);
    }

    public void deleteMailingList(String textIdentifier) {
        Long mailingListId = mailingListRepository.getMailingListId(textIdentifier);
        mailingListRepository.deleteMailingList(mailingListId);
    }

    public void addEmailsToList(String textIdentifier, List<String> emails) {
        Long mailingListId = mailingListRepository.getMailingListId(textIdentifier);
        emails.forEach(emailAddress ->
            mailingListRepository.addEmailToList(mailingListId, emailAddress));
    }

    public void deleteEmailsFromList(String textIdentifier, List<String> emails) {
        Long mailingListId = mailingListRepository.getMailingListId(textIdentifier);
        emails.forEach(emailAddress ->
                mailingListRepository.deleteEmailFromList(mailingListId, emailAddress));
    }

    public List<String> getEmailsFromList(String textIdentifier) {
        Long mailingListId = mailingListRepository.getMailingListId(textIdentifier);
        MailingList mailingList = mailingListRepository.getMailingList(mailingListId);
        return mailingList.getEmails();
    }

    public void importMailingList() throws Exception {
        List<MailingList> mailingLists = mailingListManager.importMailingLists();
        mailingLists.forEach(this::createMailingList);
    }
}
