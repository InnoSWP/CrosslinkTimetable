package com.timetable.authentication;

import com.timetable.outlook.OutlookConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationController {
    AuthenticationService authenticationService;

    @Autowired
    AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public Map<String, String> login(String email, String password) {
        try {
            authenticationService.login(email, password);
            String token = authenticationService.generateNewToken();
            Map<String, String> tokenJson = new HashMap<String, String>();
            tokenJson.put("token", token);
            return tokenJson;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}
