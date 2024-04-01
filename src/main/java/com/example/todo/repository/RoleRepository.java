package com.example.todo.repository;

import com.example.todo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role>findByAuthority(String Authority);
}
