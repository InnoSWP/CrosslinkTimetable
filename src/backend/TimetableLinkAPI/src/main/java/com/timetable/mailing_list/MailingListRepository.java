package com.timetable.mailing_list;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailingListRepository {
    List<String> getMailingListsNames();
    void createMailingList(MailingList mailingList);
    Long getMailingListId(String textIdentifier);
    MailingList getMailingList(Long id);
    List<MailingList> getAllMailingLists();
    void deleteMailingList(Long id);
    void addEmailToList(Long mailingListId, String emailAddress);
    void deleteEmailFromList(Long mailingListId, String emailAddress);
    boolean mailingListExists(String textIdentifier);
    void updateMailingList(MailingList mailingList);
    void updateTextIdentifier(String textIdentifier, String newTextIdentifier);
}
