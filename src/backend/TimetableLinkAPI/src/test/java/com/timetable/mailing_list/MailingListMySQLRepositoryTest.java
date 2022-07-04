// package com.timetable.mailing_list;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
// import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.jdbc.core.SqlOutParameter;
// import org.springframework.test.context.jdbc.Sql;

// import java.util.*;

// import static org.junit.jupiter.api.Assertions.*;

// @DataJdbcTest
// @Sql({"/h2_schema.sql"})
// class MailingListMySQLRepositoryTest {
//     JdbcTemplate jdbcTemplate;
//     MailingListMySQLRepository repo;
//     List<MailingList> mailingLists;

//     @Autowired
//     MailingListMySQLRepositoryTest(JdbcTemplate jdbcTemplate) {
//         this.jdbcTemplate = jdbcTemplate;
//         this.repo = new MailingListMySQLRepository(jdbcTemplate);
//         mailingLists = new ArrayList<>();
//         MailingList mailingList1 = new MailingList(
//                 "list1", Arrays.asList("a", "b", "c", "d"));
//         MailingList mailingList2 = new MailingList(
//                 "list2", Arrays.asList("e", "f", "g", "d"));
//         mailingLists.add(mailingList1);
//         mailingLists.add(mailingList2);
//     }

//     @Test
//     void createAndGetMailingListsNames() {
//         MailingList mailingList = mailingLists.get(0);
//         repo.createMailingList(mailingList);
//         Long id = repo.getMailingListId("list1");
//         MailingList currentMailingList = repo.getMailingList(id);
//         assertEquals(currentMailingList, mailingList);
//         System.out.println(repo.getMailingListId("list1"));
//     }

//     @Test
//     void getAllMailingLists() {
//         mailingLists.forEach(mailingList -> repo.createMailingList(mailingList));
//         Set<MailingList> allMailingLists = new HashSet<>(repo.getAllMailingLists());
//         Set<MailingList> expectedMailingLists = new HashSet<>();
//         expectedMailingLists.add(mailingLists.get(0));
//         expectedMailingLists.add(mailingLists.get(1));
//         assertEquals(expectedMailingLists, allMailingLists);

//     }

//     @Test
//     void deleteMailingList() {
//         mailingLists.forEach(mailingList -> repo.createMailingList(mailingList));
//         Long id = repo.getMailingListId("list2");
//         repo.deleteMailingList(id);
//         assertThrows(org.springframework.dao.EmptyResultDataAccessException.class, () -> repo.getMailingList(id));
//     }

//     @Test
//     void addEmailToList() {
//         repo.createMailingList(mailingLists.get(0));
//         String newEmail = "r";
//         Long id = repo.getMailingListId("list1");
//         repo.addEmailToList(id, newEmail);
//         assertTrue(repo.getMailingList(id).getEmails().contains(newEmail));
//     }

//     @Test
//     void deleteEmailFromList() {
//         repo.createMailingList(mailingLists.get(0));
//         repo.createMailingList(mailingLists.get(1));
//         List<String> emails = new ArrayList<>(List.of("a", "b", "c", "d"));
//         Long id1 = repo.getMailingListId("list1");
//         for (String emailToDelete : emails) {
//             System.out.println(repo.getEmailsByListId(id1));
//             repo.deleteEmailFromList(id1, emailToDelete);
//             assertFalse(repo.getMailingList(id1).getEmails().contains(emailToDelete));
//         }
//         Long id2 = repo.getMailingListId("list2");
//         assertTrue(repo.getMailingList(id2).getEmails().containsAll(mailingLists.get(1).getEmails()));
//         emails.addAll(List.of("e", "f", "g", "l", "t", "p"));
//         for (String emailToAdd : emails) {
//             repo.addEmailToList(id1, emailToAdd);
//             assertTrue(repo.getMailingList(id1).getEmails().contains(emailToAdd));
//         }
//         //System.out.println(repo.getMailingList(id1).getEmails().toString());
//         for (String emailToDelete : mailingLists.get(1).getEmails()) {
//             repo.deleteEmailFromList(id2, emailToDelete);
//             assertFalse(repo.getMailingList(id2).getEmails().contains(emailToDelete));
//         }
//         //System.out.println(repo.getEmailsByListId(id1).size() + repo.getEmailsByListId(id2).size());;
//     }

//     @Test
//     void addAndDeleteEmails() {
//         MailingList mailingList = mailingLists.get(0);
//         repo.createMailingList(mailingLists.get(0));
//         Long id = repo.getMailingListId(mailingList.getTextIdentifier());
//         for (String email : mailingList.getEmails()) {
//             assertTrue(repo.getEmailsByListId(id).contains(email));
//             repo.deleteEmailFromList(id, email);
//             assertFalse(repo.getEmailsByListId(id).contains(email));
//         }
//         assertEquals(repo.getEmailsByListId(id).size(), 0);
//         List<String> emailsToAdd = new ArrayList<>(mailingLists.get(0).getEmails());
//         emailsToAdd.addAll(List.of("o", "l", "t"));
//         for (String email : emailsToAdd) {
//             assertFalse(repo.getEmailsByListId(id).contains(email));
//             repo.addEmailToList(id, email);
//             assertTrue(repo.getEmailsByListId(id).contains(email));
//         }
//         List<String> currentEmails = repo.getEmailsByListId(id);
//         assertTrue(currentEmails.containsAll(emailsToAdd));
//         assertEquals(currentEmails.size(), emailsToAdd.size());
//     }

//     @Test
//     void mailingListExists() {
//         repo.createMailingList(mailingLists.get(0));
//         assertTrue(repo.mailingListExists("list1"));
//         assertFalse(repo.mailingListExists("list3"));
//     }

//     @Test
//     void updateMailingList() {
//         MailingList mailingList = mailingLists.get(0);
//         repo.createMailingList(mailingList);
//         Long id = repo.getMailingListId(mailingList.getTextIdentifier());
//         MailingList updatedMailingList = new MailingList(
//                 "list1", new ArrayList<>()
//         );
//         repo.updateMailingList(updatedMailingList);
//         assertEquals(updatedMailingList, repo.getMailingList(id));
//     }

//     @Test
//     void updateTextIdentifier() {
//         MailingList mailingList = mailingLists.get(0);
//         repo.createMailingList(mailingList);
//         Long id = repo.getMailingListId(mailingList.getTextIdentifier());
//         String newTextIdentifier = "list2";
//         repo.updateTextIdentifier(mailingList.getTextIdentifier(), newTextIdentifier);
//         assertEquals(new MailingList(newTextIdentifier, mailingList.getEmails()),
//                 repo.getMailingList(id));
//     }

//     @Test
//     void getEmailsByListId() {
//         MailingList mailingList = mailingLists.get(0);
//         repo.createMailingList(mailingList);
//         Long id = repo.getMailingListId(mailingList.getTextIdentifier());
//         assertEquals(mailingList.getEmails(), repo.getEmailsByListId(id));
//     }
// }