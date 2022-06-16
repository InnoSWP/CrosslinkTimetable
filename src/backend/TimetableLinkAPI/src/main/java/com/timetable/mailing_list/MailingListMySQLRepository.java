package com.timetable.mailing_list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class MailingListMySQLRepository implements MailingListRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MailingListMySQLRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void createMailingList(MailingList mailingList) {
        jdbcTemplate.update(
                "INSERT INTO mailingList (textIdentifier) values (?)",
                mailingList.getTextIdentifier());
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
        jdbcTemplate.update(
                "INSERT INTO email (emailAddress) VALUES (?);",
                emailAddress);
        jdbcTemplate.update(
                """
                    INSERT INTO emailBelonging (mailingListId, emailId))
                    SELECT ?, id FROM email
                    WHERE emailAddress = ?
                    """, mailingListId, emailAddress);
    }

    @Override
    public void deleteEmailFromList(Long mailingListId, String emailAddress) {
        jdbcTemplate.update(
                "DELETE FROM emailBelonging WHERE mailingListId = ?",
                mailingListId);
        jdbcTemplate.update(
                """
                    DELETE FROM email
                    WHERE id NOT IN
                    (SELECT emailId FROM emailBelonging);
                    """);
    }

    private List<String> getEmailsByListId(Long mailingListId) {
        String getEmailsSqlRequest =
                """
                 SELECT DISTINCT emailAddress FROM
                 email LEFT JOIN emailBelonging
                 ON email.id = emailBelonging.emailId
                 WHERE mailingListId = ?
                 """;
        return jdbcTemplate.queryForList(
                        getEmailsSqlRequest, String.class, mailingListId);
    }

    private String getTextIdentifier(Long mailingListId) {
        String getMailingListTextIdentifierSql =
                "SELECT textIdentifier FROM mailingList WHERE id = ?";
        return jdbcTemplate.queryForObject(
                getMailingListTextIdentifierSql, String.class, mailingListId);

    }
}
