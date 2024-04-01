package com.example.todo.repository;

import com.example.todo.model.AppUSer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUSer,Integer> {
    Optional<AppUSer>findByUsername(String username);
}
