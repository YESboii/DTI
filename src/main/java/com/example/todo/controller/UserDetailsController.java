package com.example.todo.controller;

import com.example.todo.model.AppUSer;
import com.example.todo.repository.UserRepository;
import com.example.todo.service.EmailService;
import com.example.todo.service.OTPGenerator;
import com.example.todo.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/inboxmaster")
public class UserDetailsController {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final OTPGenerator otpGenerator;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/profile")
    public String profileGet(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AppUSer appUSer = userRepository.findByUsername(username).get();
        model.addAttribute("user",appUSer);
        return "profile";
    }
    @GetMapping("/change-password")
    public String verifyOtp(HttpSession session){
        int otp = otpGenerator.otpGenerator();
        session.setAttribute("otp",otp);
        String subject = "OTP Verfication";
        String message = ""+"<div>"+
                "<h1>"+"Hello user, Your 5 digit otp to change password is."+"<b>"+"OTP is  "+otp+"</n>"+"<h1>"
                +"</div>";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean flag = this.emailService.sendEmail(subject,message,username);
        return "verifypass";
    }
    @GetMapping ("/new-password")
    public String newPassword(@ModelAttribute("otp") int otp1, HttpSession session, Model model){
        int otp = (Integer)session.getAttribute("otp");
        if(otp1!=otp){
            model.addAttribute("errorMessage","Invalid OTP. Please try again.");
            return "verifypass";
        }
        return "newpassword";
    }
    @PostMapping ("/new-password")
    @Transactional
    public String changePassword(@ModelAttribute("password") String password,Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AppUSer appUSer = userRepository.findByUsername(username).get();
        String encodedPassword = passwordEncoder.encode(password);
        appUSer.setPassword(encodedPassword);
        userRepository.save(appUSer);
        model.addAttribute("user",appUSer);
        model.addAttribute("passChanged","Password changed successfully");
        return "profile";
    }

}
