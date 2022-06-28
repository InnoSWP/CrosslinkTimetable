package com.timetable.mailing_list;

import microsoft.exchange.webservices.data.property.complex.EmailAddress;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailingList that = (MailingList) o;
        return textIdentifier.equals(that.textIdentifier)
                && (new HashSet<>(emails)).equals(new HashSet<>(that.emails));
    }

    @Override
    public int hashCode() {
        return Objects.hash(textIdentifier, new HashSet<>(emails));
    }
}
