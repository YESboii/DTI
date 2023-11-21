package com.example.todo.repository;


import com.example.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.sql.In;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
}
