package com.timetable.mailing_list;

import microsoft.exchange.webservices.data.core.service.item.Contact;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.core.service.schema.ServiceObjectSchema;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/mailingLists")
public class MailingListController {
    MailingListService mailingListService;

    @Autowired
    public MailingListController(MailingListService mailingListService) {
        this.mailingListService = mailingListService;
    }

    @PostMapping
    public void createMailingList(@RequestBody MailingList mailingList) {
        mailingListService.createMailingList(mailingList);
    }

    @DeleteMapping("/{textIdentifier}")
    public void deleteMailingList(@PathVariable String textIdentifier) {
        mailingListService.deleteMailingList(textIdentifier);
    }

    @PutMapping("/{textIdentifier}/emails")
    public void addEmailsToList(@PathVariable String textIdentifier,
                                @RequestBody List<String> emails) {
        mailingListService.addEmailsToList(textIdentifier, emails);
    }

    @DeleteMapping("/{textIdentifier}/emails")
    public void deleteEmailsFromList(@PathVariable String textIdentifier,
                                     @RequestBody List<String> emails) {
        mailingListService.deleteEmailsFromList(textIdentifier, emails);
    }

    @GetMapping("/{textIdentifier}/emails")
    public List<String> getEmailsFormList(@PathVariable String textIdentifier) {
        return mailingListService.getEmailsFromList(textIdentifier);
    }

    @GetMapping("/mailingListsNames")
    public List<String> getMailingListsNames() {
        return mailingListService.getMailingListsNames();
    }

    @PatchMapping("/importOutlookMailingLists")
    public void importEmailingListsFromOutlook() {
        try {
            mailingListService.importMailingList();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
