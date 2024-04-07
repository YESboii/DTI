package com.example.todo.controller;

import com.example.todo.model.AppUSer;
import com.example.todo.repository.UserRepository;
import com.example.todo.service.AuthenticationService;
import com.example.todo.service.EmailService;
import com.example.todo.service.OTPGenerator;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
@Controller
@RequestMapping("/inboxmaster/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final EmailService emailService;
    private final OTPGenerator otpGenerator;
    private final UserRepository userRepository;
    @RequestMapping(value = "/signin",method = RequestMethod.GET)
    public String signInPage(Model model){
        return "signin";
    }
    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String registerPage(Model model){
        model.addAttribute("user", new AppUSer());
        return "signup";
    }

    @PostMapping("/register")
    public String authenticate(@ModelAttribute AppUSer appUSer, HttpSession session,Model model){
//         authenticationService.registerUser(appUSer);
        boolean flag1 =  userRepository.findByUsername(appUSer.getUsername()).isPresent();
        if (flag1){
            model.addAttribute("errorMessage", "User already exists");
            return "signin";
        }
        session.setAttribute("userToBeRegistered",appUSer);
        int otp = otpGenerator.otpGenerator();
        session.setAttribute("otp",otp);
        String subject = "OTP Verfication";
        String message = ""+"<div>"+
                "<h1>"+"Hello user, Please enter the 5 digit OTP to Signup."+"<b>"+"OTP is  "+otp+"</n>"+"<h1>"
                +"</div>";
        boolean flag = this.emailService.sendEmail(subject,message,appUSer.getUsername());
         return "verifyotp";
    }
    @PostMapping("/verifyotp")
    public String verify(@RequestParam("otp") int otpEntered, HttpSession session, Model model) {
        int otp = (Integer) session.getAttribute("otp");
        AppUSer appUser = (AppUSer) session.getAttribute("userToBeRegistered");
            System.out.println(appUser.toString());
        if (otp == otpEntered) {
            authenticationService.registerUser(appUser);
            return "redirect:/inboxmaster/auth/signin"; // Redirect to sign-in page after successful registration
        } else {
            model.addAttribute("errorMessage", "Invalid OTP. Please try again."); // Add error message to model
            return "verifyotp"; // Return to the OTP verification page
        }
    }

}
