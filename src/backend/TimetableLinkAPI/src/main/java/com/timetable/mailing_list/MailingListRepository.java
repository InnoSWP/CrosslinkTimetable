package com.timetable.mailing_list;

import org.springframework.stereotype.Repository;

@Repository
public interface MailingListRepository {
    void createMailingList(MailingList mailingList);
    Long getMailingListId(String textIdentifier);
    MailingList getMailingList(Long id);
    void deleteMailingList(Long id);
    void addEmailToList(Long mailingListId, String emailAddress);
    void deleteEmailFromList(Long mailingListId, String emailAddress);
}
