package com.timetable;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BusinessLogic {
    List<String> emails;
    String name;

    public BusinessLogic() {
        emails = new ArrayList<>();
    }

    public void addEmail(String email) {
        emails.add(email);
    }

    public boolean emailExists(String email) {
        boolean exists = false;
        for (String cur : emails) {
            if (cur.equals(email)) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    public void deleteEmail(String email) {
        for (int i = 0; i < emails.size(); i++) {
            if (emails.get(i).equals(email)) {
                emails.remove(i);
            }
        }
    }

    public int getSize() {
        return emails.size();
    }
}
