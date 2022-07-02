package com.timetable.authentication;

import com.timetable.outlook.OutlookConnector;
import com.timetable.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        try {
            authenticationService.login(user.getEmail(), user.getPassword());
            String token = authenticationService.generateNewToken();
            Map<String, String> tokenJson = new HashMap<>();
            tokenJson.put("token", token);
            return tokenJson;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}
