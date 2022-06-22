package com.timetable;

//import com.timetable.jdbc.SpringJdbcConfig;
import com.timetable.jdbc.SpringJdbcConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@SpringBootApplication
@Import({SpringJdbcConfig.class})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
