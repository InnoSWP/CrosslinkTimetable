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
<<<<<<< HEAD
=======
import org.springframework.web.servlet.config.annotation.*;
>>>>>>> main

import javax.sql.DataSource;

@SpringBootApplication
@Import({SpringJdbcConfig.class})
<<<<<<< HEAD
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
=======
//@EnableWebMvc
public class App  {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("forward:/index.html");
//    }

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
//            }
//        };
//    }
>>>>>>> main
}
