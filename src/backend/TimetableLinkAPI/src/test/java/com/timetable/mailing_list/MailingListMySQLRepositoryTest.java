package com.timetable.mailing_list;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@Sql({"/h2_schema.sql"})
class MailingListMySQLRepositoryTest {
    JdbcTemplate jdbcTemplate;
    MailingListMySQLRepository repo;
    List<MailingList> mailingLists;

    @Autowired
    MailingListMySQLRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.repo = new MailingListMySQLRepository(jdbcTemplate);
        mailingLists = new ArrayList<>();
        MailingList mailingList1 = new MailingList(
                "list1", Arrays.asList("a", "b", "c", "d"));
        MailingList mailingList2 = new MailingList(
                "list2", Arrays.asList("e", "f", "g", "d"));
        mailingLists.add(mailingList1);
        mailingLists.add(mailingList2);
    }

    @Test
    void createAndGetMailingListsNames() {
        MailingList mailingList = mailingLists.get(0);
        repo.createMailingList(mailingList);
        Long id = repo.getMailingListId("list1");
        MailingList currentMailingList = repo.getMailingList(id);
        assertEquals(currentMailingList, mailingList);
        System.out.println(repo.getMailingListId("list1"));
    }

    @Test
    void getAllMailingLists() {
        mailingLists.forEach(mailingList -> repo.createMailingList(mailingList));
        Set<MailingList> allMailingLists = new HashSet<>(repo.getAllMailingLists());
        Set<MailingList> expectedMailingLists = new HashSet<>();
        expectedMailingLists.add(mailingLists.get(0));
        expectedMailingLists.add(mailingLists.get(1));
        assertEquals(expectedMailingLists, allMailingLists);

    }

    @Test
    void deleteMailingList() {
        mailingLists.forEach(mailingList -> repo.createMailingList(mailingList));
        Long id = repo.getMailingListId("list2");
        repo.deleteMailingList(id);
        assertThrows(org.springframework.dao.EmptyResultDataAccessException.class, () -> repo.getMailingList(id));
    }

    @Test
    void addEmailToList() {
        repo.createMailingList(mailingLists.get(0));
        String newEmail = "r";
        Long id = repo.getMailingListId("list1");
        repo.addEmailToList(id, newEmail);
        assertTrue(repo.getMailingList(id).getEmails().contains(newEmail));
    }

    @Test
    void deleteEmailFromList() {
        repo.createMailingList(mailingLists.get(0));
        String emailToDelete = "a";
        Long id = repo.getMailingListId("list1");
        repo.deleteEmailFromList(id, emailToDelete);
        assertFalse(repo.getMailingList(id).getEmails().contains(emailToDelete));
    }

    @Test
    void mailingListExists() {
        repo.createMailingList(mailingLists.get(0));
        assertTrue(repo.mailingListExists("list1"));
        assertFalse(repo.mailingListExists("list3"));
    }

    @Test
    void updateMailingList() {
        MailingList mailingList = mailingLists.get(0);
        repo.createMailingList(mailingList);
        Long id = repo.getMailingListId(mailingList.getTextIdentifier());
        MailingList updatedMailingList = new MailingList(
                "list1", new ArrayList<>()
        );
        repo.updateMailingList(updatedMailingList);
        assertEquals(updatedMailingList, repo.getMailingList(id));
    }

    @Test
    void updateTextIdentifier() {
        MailingList mailingList = mailingLists.get(0);
        repo.createMailingList(mailingList);
        Long id = repo.getMailingListId(mailingList.getTextIdentifier());
        String newTextIdentifier = "list2";
        repo.updateTextIdentifier(mailingList.getTextIdentifier(), newTextIdentifier);
        assertEquals(new MailingList(newTextIdentifier, mailingList.getEmails()),
                repo.getMailingList(id));
    }

    @Test
    void getEmailsByListId() {
        MailingList mailingList = mailingLists.get(0);
        repo.createMailingList(mailingList);
        Long id = repo.getMailingListId(mailingList.getTextIdentifier());
        assertEquals(mailingList.getEmails(), repo.getEmailsByListId(id));
    }
}