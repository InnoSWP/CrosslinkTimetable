package com.timetable.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/index")
    public String index() {
        return "index.html";
    }

    @GetMapping("/create/mailing")
    public String create_mailing(){ return "post_mailing_list.html";}

    @GetMapping("/edit/mailing")
    public String edit_mailing(){ return "edit_mailing_list.html";}
}

