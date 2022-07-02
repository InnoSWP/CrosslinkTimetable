package com.timetable.authentication;

import com.timetable.outlook.OutlookConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
@PropertySource("classpath:security.properties")
public class AuthenticationService {
    private final SecureRandom secureRandom = new SecureRandom();
    private final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    private final OutlookConnector outlookConnector;
    private final AuthenticationRepository authenticationRepository;
    private final Long tokenLifetime;

    @Autowired
    AuthenticationService(OutlookConnector outlookConnector,
                          AuthenticationRepository authenticationRepository,
                          @Value("${token.lifetime}") Long tokenLifetime) {
        this.outlookConnector = outlookConnector;
        this.authenticationRepository = authenticationRepository;
        this.tokenLifetime = tokenLifetime;
    }

    public void login(String email, String password) throws Exception {
        outlookConnector.setCredentials(email, password);
    }

    public String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        String token = base64Encoder.encodeToString(randomBytes);
        Long currentTime = System.currentTimeMillis();
        authenticationRepository.saveToken(token, currentTime + tokenLifetime);
        return token;
    }

    public boolean checkToken(String token) {
        Long currentTime = System.currentTimeMillis();
        authenticationRepository.renewTokens(currentTime);
        return authenticationRepository.tokenExists(token);
    }
}
