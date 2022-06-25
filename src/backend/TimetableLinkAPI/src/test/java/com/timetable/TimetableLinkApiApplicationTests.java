package com.timetable;

import com.timetable.mailing_list.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;


//@SpringBootTest
class TimetableLinkApiApplicationTests {
    BusinessLogic businessLogic;

    //@Autowired
    TimetableLinkApiApplicationTests() {
        this.businessLogic = new BusinessLogic();
    }

    @Test
    void canAddEmails() {
        businessLogic.addEmail("a");
        assertTrue(businessLogic.emailExists("a"));
    }

    @Test
    void canDeleteEmails() {
        businessLogic.deleteEmail("a");
        assertFalse(businessLogic.emailExists("a"));
    }

    @Test
    void canCountNumberOfEmails() {
        businessLogic.addEmail("b");
        businessLogic.addEmail("c");
        businessLogic.deleteEmail("a");
        businessLogic.addEmail("d");
        businessLogic.addEmail("e");
        assertEquals(businessLogic.getSize(), 4);
    }
}
