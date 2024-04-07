package com.example.todo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MailSessionDefinition;

@Controller
@RequestMapping("/inboxmaster")
public class GeneralController {
    @GetMapping ("/")
    public String landingPage(){
        return "landingpage";
    }
    @GetMapping("/contactus")
    public String contactUs(){
        return "contactus";
    }



}
