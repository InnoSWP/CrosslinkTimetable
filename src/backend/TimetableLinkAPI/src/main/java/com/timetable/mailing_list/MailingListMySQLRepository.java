package com.timetable.mailing_list;

import microsoft.exchange.webservices.data.core.service.item.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class MailingListMySQLRepository implements MailingListRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MailingListMySQLRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<String> getMailingListsNames() {
        return jdbcTemplate.queryForList(
                "SELECT textIdentifier FROM mailingList", String.class);
    }

    @Override
    public void createMailingList(MailingList mailingList) {
        String textIdentifier = mailingList.getTextIdentifier();

        try {
            jdbcTemplate.update(
                    "INSERT INTO mailingList (textIdentifier) values (?)",
                    textIdentifier);
        } catch (Exception ignored) {}

        Long mailingListId = getMailingListId(textIdentifier);
        mailingList.getEmails().forEach(email -> addEmailToList(mailingListId, email));
    }

    @Override
    public Long getMailingListId(String textIdentifier) {
        /*
        RowMapper<Long> rowMapper = new RowMapper<Long>() {
            public Long mapRow(ResultSet resultSet, int rowNum)
                    throws SQLException{
                return resultSet.getLong(rowNum);
            }
        };*/
        return jdbcTemplate.queryForObject(
                "SELECT id FROM mailingList WHERE textIdentifier = ?",
                Long.class, textIdentifier);
    }

    @Override
    public MailingList getMailingList(Long mailingListId) {
        List<String> suitableEmails =
                getEmailsByListId(mailingListId);
        String textIdentifier = getTextIdentifier(mailingListId);
        return new MailingList(textIdentifier, suitableEmails);
    }

    @Override
    public List<MailingList> getAllMailingLists() {
        List<Long> ids = jdbcTemplate.queryForList(
                "SELECT id FROM mailingList", Long.class);
        return ids.stream()
                .map(this::getMailingList)
                .toList();
    }


    @Override
    public void deleteMailingList(Long mailingListId) {
        jdbcTemplate.update(
                "DELETE FROM mailingList WHERE id = ?",
                mailingListId);
        jdbcTemplate.update(
                "DELETE FROM emailBelonging WHERE mailingListId = ?",
                mailingListId);
    }

    @Override
    public void addEmailToList(Long mailingListId, String emailAddress) {
        try {
            jdbcTemplate.update(
                    """
                        INSERT INTO email (emailAddress) VALUES (?)
                        """, emailAddress);
        }
        catch (Exception ignored) {}
        jdbcTemplate.update(
                """
                    INSERT INTO emailBelonging (mailingListId, emailId)
                    SELECT ?, id FROM email
                    WHERE emailAddress = ?
                    """, mailingListId, emailAddress);
    }

    @Override
    public void deleteEmailFromList(Long mailingListId, String emailAddress) {
        Long emailId = jdbcTemplate.queryForObject(
                """
                    SELECT email.id FROM email
                    JOIN emailBelonging ON email.id = emailBelonging.emailId
                    WHERE email.emailAddress = ?
                    LIMIT 1;
                    """, Long.class, emailAddress);
        jdbcTemplate.update(
                "DELETE FROM emailBelonging WHERE mailingListId = ? AND emailId = ?",
                mailingListId, emailId);
        List<String> list = jdbcTemplate.queryForList(
                "SELECT id FROM emailBelonging", String.class
        );
        System.out.println(list + " " + list.size());
        jdbcTemplate.update(
                """
                    DELETE FROM email
                    WHERE id NOT IN
                    (SELECT emailId FROM emailBelonging);
                    """);
    }

    @Override
    public boolean mailingListExists(String textIdentifier) {
        Long count = jdbcTemplate.queryForObject(
                "SELECT count(id) FROM mailingList WHERE textIdentifier = ?", Long.class, textIdentifier);
        return count != null && count.compareTo(0L) > 0;
    }

    @Override
    public void updateMailingList(MailingList mailingList) {
        Long id = getMailingListId(mailingList.getTextIdentifier());
        Set<String> oldEmails = new HashSet<>(getEmailsByListId(id));
        Set<String> newEmails = new HashSet<>(mailingList.getEmails());
        Set<String> emailsToDelete = new HashSet<>(oldEmails);
        emailsToDelete.removeAll(newEmails);
        Set<String> emailsToAdd = new HashSet<>(newEmails);
        emailsToAdd.removeAll(oldEmails);
        emailsToDelete.forEach(email -> deleteEmailFromList(id, email));
        emailsToAdd.forEach(email -> addEmailToList(id, email));
    }

    @Override
    public void updateTextIdentifier(String textIdentifier, String newTextIdentifier) {
        jdbcTemplate.update(
                """
                   UPDATE mailingList
                   SET textIdentifier = ?
                   WHERE textIdentifier = ?;
                   """, newTextIdentifier, textIdentifier);
    }


    @Override
    public List<String> getEmailsByListId(Long mailingListId) {
        String getEmailsSqlRequest =
                """
                 SELECT DISTINCT emailAddress FROM
                 email LEFT JOIN emailBelonging
                 ON email.id = emailBelonging.emailId
                 WHERE mailingListId = ?;
                 """;
        return jdbcTemplate.queryForList(
                        getEmailsSqlRequest, String.class, mailingListId);
    }

    public void init() {
        jdbcTemplate.update(
                """
                    CREATE DATABASE IF NOT EXISTS timetable;
                    USE timetable;
                    
                    SET SQL_SAFE_UPDATES = 0;
                    
                    CREATE TABLE IF NOT EXISTS mailingList (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        textIdentifier VARCHAR(40) UNIQUE
                    );
                    
                    CREATE TABLE IF NOT EXISTS email(
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        emailAddress VARCHAR(60) UNIQUE
                    );
                    
                    CREATE TABLE IF NOT EXISTS emailBelonging(
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        emailId BIGINT,
                        mailingListId BIGINT,
                        FOREIGN KEY (emailId) REFERENCES email (id) ON DELETE CASCADE,
                        FOREIGN KEY (mailingListId) references mailingList (id) ON DELETE CASCADE
                    );
                    
                    CREATE TABLE IF NOT EXISTS event(
                        outlookAppointmentId varchar(400) PRIMARY KEY
                    );
                    """
        );
    }

    private String getTextIdentifier(Long mailingListId) {
        String getMailingListTextIdentifierSql =
                "SELECT textIdentifier FROM mailingList WHERE id = ?";
        return jdbcTemplate.queryForObject(
                getMailingListTextIdentifierSql, String.class, mailingListId);

    }

}
