package com.timetable.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuthenticationMySQLRepository implements AuthenticationRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthenticationMySQLRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveToken(String token, Long expireTime) {
        jdbcTemplate.update(
                "INSERT INTO token (content, expireTime) values (?, ?)",
                token, expireTime);
    }

    @Override
    public void renewTokens(Long currentTime) {
        jdbcTemplate.update(
                "DELETE FROM token WHERE expireTime < ?", currentTime);
    }

    @Override
    public boolean tokenExists(String token) {
        Long exists = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM token WHERE content = ?", Long.class, token);
        return exists != null && exists.compareTo(0L) > 0;
    }
}
