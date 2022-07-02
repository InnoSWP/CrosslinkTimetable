package com.timetable.mailing_list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        System.out.println(emails.toString());
        emails.forEach(emailAddress ->
            mailingListRepository.addEmailToList(mailingListId, emailAddress));
    }

    public void deleteEmailsFromList(String textIdentifier, List<String> emails) {
        Long mailingListId = mailingListRepository.getMailingListId(textIdentifier);
        for (String emailAddress : emails) {
            try {
                System.out.println("before " + getEmailsFromList(textIdentifier).toString());
                mailingListRepository.deleteEmailFromList(mailingListId, emailAddress);
                System.out.println("after " + getEmailsFromList(textIdentifier).toString());
            } catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public MailingList getMailingList(String textIdentifier) {
        Long mailingListId = mailingListRepository.getMailingListId(textIdentifier);
        return mailingListRepository.getMailingList(mailingListId);
    }

    public List<MailingList> getAllMailingLists() {
        return mailingListRepository.getAllMailingLists();
    }

    public List<String> getEmailsFromList(String textIdentifier) {
        MailingList mailingList = getMailingList(textIdentifier);
        return mailingList.getEmails();
    }

    public void importMailingList() throws Exception {
        List<MailingList> mailingLists = mailingListManager.importMailingLists();
        mailingLists.forEach(mailingList -> {
            // Long id = mailingListRepository.getMailingListId(mailingList.getTextIdentifier());
            if (mailingListRepository.mailingListExists(mailingList.getTextIdentifier())) {
                updateExistingMailingList(mailingList);
            } else {
               mailingListRepository.createMailingList(mailingList);
            }
        });
    }

    public void cancelInvitations(String eventId, String textIdentifier) throws Exception {
        List<String> emails = getEmailsFromList(textIdentifier);
        mailingListManager.cancelInvitations(eventId, emails);
    }

    public void updateTextIdentifier(String textIdentifier, String newTextIdentifier) {
        mailingListRepository.updateTextIdentifier(textIdentifier, newTextIdentifier);
    }

    private void updateExistingMailingList(MailingList mailingList) {
        Long id = mailingListRepository.getMailingListId(mailingList.getTextIdentifier());
        Set<String> oldEmails = new HashSet<>(mailingListRepository.getEmailsByListId(id));
        Set<String> newEmails = new HashSet<>(mailingList.getEmails());
        Set<String> emailsToDelete = new HashSet<>(oldEmails);
        emailsToDelete.removeAll(newEmails);
        Set<String> emailsToAdd = new HashSet<>(newEmails);
        emailsToAdd.removeAll(oldEmails);
        emailsToDelete.forEach(email ->
                mailingListRepository.deleteEmailFromList(id, email));
        emailsToAdd.forEach(email ->
                mailingListRepository.addEmailToList(id, email));
    }
}
