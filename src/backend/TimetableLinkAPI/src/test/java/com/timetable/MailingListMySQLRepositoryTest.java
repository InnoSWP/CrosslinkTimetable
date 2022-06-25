//package com.timetable;
//
//import com.timetable.mailing_list.MailingList;
//import com.timetable.mailing_list.MailingListMySQLRepository;
//import com.timetable.mailing_list.MailingListRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.jdbc.Sql;
//
//import java.util.Arrays;
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJdbcTest
//@Sql({"../../../../../init.sql"})
//class MailingListMySQLRepositoryTest {
//    private final MailingListRepository repo;
//
//    @Autowired
//    MailingListMySQLRepositoryTest(JdbcTemplate jdbcTemplate) {
//        repo = new MailingListMySQLRepository(jdbcTemplate);
//    }
//
//    @Test
//    void createMailingListAndExtractIt() {
//        MailingList mailingList = new MailingList("list",
//                Arrays.asList("a", "b", "c", "d"));
//        repo.createMailingList(mailingList);
//        System.out.println(repo.getMailingListId("list"));
//        assertEquals(1, 2);
//        assertEquals(mailingList, repo.getMailingList(repo.getMailingListId("list")));
//    }
//


//    @Test
//    void getMailingListId() {
//    }
//
//    @Test
//    void getMailingList() {
//    }
//
//    @Test
//    void getAllMailingLists() {
//    }
//
//    @Test
//    void deleteMailingList() {
//    }
//
//    @Test
//    void addEmailToList() {
//    }
//
//    @Test
//    void deleteEmailFromList() {
//    }
//
//    @Test
//    void mailingListExists() {
//    }
//
//    @Test
//    void updateMailingList() {
//    }
//
//    @Test
//    void updateTextIdentifier() {
//    }
//
//    @Test
//    void getEmailsByListId() {
//    }
//}