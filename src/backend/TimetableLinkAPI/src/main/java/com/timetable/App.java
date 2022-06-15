package com.timetable;

import com.timetable.event.Event;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@SpringBootApplication
public class App {
//    @GetMapping
//    public Event showEvent() {
//        Date now = new Date();
//        Date start = new Date(now.getTime() + 7200000);
//        Date end = new Date(now.getTime() + 14400000);
//        return new Event(1L, "Appointment 1", start, end);
//    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
