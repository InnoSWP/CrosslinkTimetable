package com.timetable.mailing_list;

import java.util.List;

public interface MailingListManager {
    List<MailingList> importMailingLists() throws Exception;
    void cancelInvitations(String eventId, List<String> emails) throws Exception;
}
