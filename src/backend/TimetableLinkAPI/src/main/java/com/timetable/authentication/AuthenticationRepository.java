package com.timetable.authentication;

public interface AuthenticationRepository {
    void saveToken(String token, Long expireTime);
    void renewTokens(Long currentTime);
    boolean tokenExists(String token);
}
