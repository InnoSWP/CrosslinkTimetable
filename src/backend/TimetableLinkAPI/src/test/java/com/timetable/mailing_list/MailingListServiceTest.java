// package com.timetable.mailing_list;

// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.ArgumentCaptor;
// import org.mockito.Mock;
// import org.mockito.Mockito;
// import org.mockito.MockitoAnnotations;
// import org.springframework.boot.test.mock.mockito.MockBean;

// import java.util.Arrays;
// import java.util.List;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyLong;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;

// class MailingListServiceTest {
//     @Mock
//     private MailingListMySQLRepository repo;
//     @Mock
//     private OutlookMailingListManager manager;
//     private AutoCloseable autoCloseable;
//     private MailingListService underTest;
//     MailingList mailingList;

//     MailingListServiceTest() {
//         mailingList = new MailingList(
//                 "list1", Arrays.asList("a", "b", "c", "d"));
//     }

//     @BeforeEach
//     void setUp() {
//         autoCloseable = MockitoAnnotations.openMocks(this);
//         underTest = new MailingListService(repo, manager);
//     }

//     @AfterEach
//     void tearDown() throws Exception {
//         autoCloseable.close();
//     }

//     @Test
//     void getMailingListsNames() {
//         underTest.getMailingListsNames();

//         verify(repo).getMailingListsNames();
//     }

//     @Test
//     void createMailingList() {
//         underTest.createMailingList(mailingList);

//         verify(repo, times(1)).createMailingList(mailingList);
//     }

//     @Test
//     void deleteMailingList() {
//         underTest.createMailingList(mailingList);

//         verify(repo, times(1)).createMailingList(mailingList);
//     }

//     @Test
//     void addEmailsToList() {
//         List<String> newEmails = List.of("k", "l");
//         Mockito.doReturn(1L)
//                 .when(repo)
//                 .getMailingListId(mailingList.getTextIdentifier());

//         underTest.addEmailsToList("list1", newEmails);

//         verify(repo, times(1)).getMailingListId(mailingList.getTextIdentifier());

//         for (String email : newEmails) {
//             verify(repo, times(1)).addEmailToList(1L, email);
//         }
//     }

//     @Test
//     void deleteEmailsFromList() {
//         List<String> newEmails = List.of("k", "l");
//         Mockito.doReturn(1L)
//                 .when(repo)
//                 .getMailingListId(mailingList.getTextIdentifier());

//         underTest.deleteEmailsFromList("list1", newEmails);

//         verify(repo, times(1)).getMailingListId(mailingList.getTextIdentifier());

//         for (String email : newEmails) {
//             verify(repo, times(1)).deleteEmailFromList(1L, email);
//         }
//     }
// }