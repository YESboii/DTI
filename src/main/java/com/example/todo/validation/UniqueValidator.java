package com.example.todo.validation;

import com.example.todo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;



public class UniqueValidator implements ConstraintValidator<Unique, String> {

    UserRepository userRepository;
    HttpServletRequest request;

    UniqueValidator(UserRepository userRepository, HttpServletRequest request){
        this.userRepository = userRepository;
        this.request = request;
    }

    @Override
    public void initialize(Unique constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Get the email value from the request
        String email = request.getParameter("username");
        System.out.println(email);
        // Check if the email exists in the database
        boolean flag =  userRepository.findByUsername(email).isPresent();
        System.out.println(flag);
        System.out.println(userRepository.findByUsername(email).get().toString());
        if(flag) return false;

        context.buildConstraintViolationWithTemplate("Username already exists").addConstraintViolation();
        return true;
    }
}
