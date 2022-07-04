package com.timetable.authentication;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestAuthenticationCheck implements HandlerInterceptor {
    private final AuthenticationService authenticationService;

    @Autowired
    public RequestAuthenticationCheck(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //System.out.println(request.getServletPath());
        if (request.getServletPath().equals("/") ||
                request.getServletPath().equals("/login") ||
                request.getServletPath().endsWith(".html") ||
                request.getServletPath().endsWith(".js") ||
                request.getServletPath().endsWith(".css") ||
                request.getServletPath().endsWith(".ico"))
            return true;
        System.out.println("catch");
        String token = request.getHeader("Authorization");
        if (authenticationService.checkToken(token))
            return true;
        else throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
}
