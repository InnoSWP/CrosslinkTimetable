package com.timetable.mailing_list;

import com.timetable.outlook.OutlookConnector;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.property.BasePropertySet;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.service.ConflictResolutionMode;
import microsoft.exchange.webservices.data.core.exception.service.local.ServiceLocalException;
import microsoft.exchange.webservices.data.core.service.folder.ContactsFolder;
import microsoft.exchange.webservices.data.core.service.item.Contact;
import microsoft.exchange.webservices.data.core.service.item.ContactGroup;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.core.service.schema.ContactSchema;
import microsoft.exchange.webservices.data.property.complex.GroupMember;
import microsoft.exchange.webservices.data.property.complex.GroupMemberCollection;
import microsoft.exchange.webservices.data.property.complex.ItemId;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.ItemView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

@Component
public class OutlookMailingListManager {
    private final ExchangeService service;

    @Autowired
    public OutlookMailingListManager(OutlookConnector connector) {
        service = connector.getService();
    }

    public List<MailingList> importMailingLists() throws Exception {
        ArrayList<MailingList> mailingLists = new ArrayList<>();
        ContactsFolder contactsFolder = ContactsFolder.bind(service,
                WellKnownFolderName.Contacts);
        ItemView view = new ItemView(contactsFolder.getTotalCount());
        view.setPropertySet(new PropertySet(BasePropertySet.IdOnly, ContactSchema.DisplayName));
        FindItemsResults<Item> contactItems = service.findItems(WellKnownFolderName.Contacts, view);
        for (Item item : contactItems.getItems()) {
            if (item instanceof ContactGroup contactGroup) {
                contactGroup.load();
                ArrayList<String> emails = new ArrayList<>();
                String mailingListName = contactGroup.getDisplayName();
                GroupMemberCollection members = contactGroup.getMembers();
                for (GroupMember member : members) {
                    String email = member.getAddressInformation().getAddress();
                    emails.add(email);
                }
                MailingList mailingList = new MailingList(mailingListName, emails);
                mailingLists.add(mailingList);
            }
        }
        return mailingLists;
    }
}
