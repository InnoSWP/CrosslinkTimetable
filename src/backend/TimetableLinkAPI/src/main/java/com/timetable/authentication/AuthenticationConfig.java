package com.timetable.authentication;

import com.timetable.jdbc.SpringJdbcConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan
public class AuthenticationConfig extends WebMvcConfigurerAdapter {

    private final RequestAuthenticationCheck authenticationInterceptor;

    @Autowired
    public AuthenticationConfig(RequestAuthenticationCheck authenticationInterceptor) {
        this.authenticationInterceptor = authenticationInterceptor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor);
    }
}
