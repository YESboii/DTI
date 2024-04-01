package com.example.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inboxmaster")
public class GeneralController {
    @RequestMapping("/")
    public String landingPage(){
        return "landingpage";
    }
    @RequestMapping("/contactus")
    public String contactUs(){
        return "contactus";
    }
    @RequestMapping("/auth/signin")
    public String signin(){
        return "signin";
    }
}
