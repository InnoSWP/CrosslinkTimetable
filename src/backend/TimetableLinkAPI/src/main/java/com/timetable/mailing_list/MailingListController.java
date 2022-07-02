package com.timetable.mailing_list;

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

    @PatchMapping("/{textIdentifier}/emails/add")
    public void addEmailsToList(@PathVariable String textIdentifier,
                                @RequestBody List<String> emails) {
        mailingListService.addEmailsToList(textIdentifier, emails);
    }

    @PatchMapping("/{textIdentifier}/emails/delete")
    public void deleteEmailsFromList(@PathVariable String textIdentifier,
                                     @RequestBody List<String> emails) {
        mailingListService.deleteEmailsFromList(textIdentifier, emails);
    }

    @PatchMapping("/{textIdentifier}/emails/update")
    public void updateEmailsFromList(@PathVariable String textIdentifier,
                                     @RequestBody List<String> emails) {
        mailingListService.updateEmailsFromList(textIdentifier, emails);
    }

    @PatchMapping("/{textIdentifier}")
    public void updateTextIdentifier(
            @PathVariable String textIdentifier,
            @RequestParam String newTextIdentifier) {
        mailingListService.updateTextIdentifier(textIdentifier, newTextIdentifier);
    }


    @GetMapping("/{textIdentifier}/emails")
    public List<String> getEmailsFormList(@PathVariable String textIdentifier) {
        return mailingListService.getEmailsFromList(textIdentifier);
    }

    @GetMapping("/{textIdentifier}")
    public MailingList getMailingList(@PathVariable String textIdentifier) {
        return mailingListService.getMailingList(textIdentifier);
    }

    @GetMapping()
    public List<MailingList> getAllMailingLists() {
        return mailingListService.getAllMailingLists();
    }

    @GetMapping("/names")
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
