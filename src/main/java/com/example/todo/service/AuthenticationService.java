package com.example.todo.service;

import com.example.todo.model.AppUSer;
import com.example.todo.model.Role;
import com.example.todo.repository.RoleRepository;
import com.example.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

    private  final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AppUSer registerUser(AppUSer appUSer){
        appUSer.setPassword(passwordEncoder.encode(appUSer.getPassword()));
        Role role = roleRepository.findByAuthority("user").get();
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        appUSer.setAuthorities(roles);
        return userRepository.save(appUSer);
    }
}
