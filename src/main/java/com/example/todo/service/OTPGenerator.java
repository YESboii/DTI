package com.example.todo.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class OTPGenerator {
    public  int otpGenerator(){
        int otp = 0;

        SecureRandom secureRandom = new SecureRandom();
        otp = secureRandom.nextInt(90000) + 10000; // Generate a random 5-digit number
        // Combine the OTP with the current timestamp to make it more unique
//            long timestamp = Instant.now().toEpochMilli();
//            otp = Integer.parseInt(Long.toString(otp) + Long.toString(timestamp).substring(10));
        // Check if the OTP is unique by querying your database or cache

        return otp;
    }
}
