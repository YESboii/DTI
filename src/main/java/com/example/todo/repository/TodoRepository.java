package com.example.todo.repository;


import com.example.todo.model.AppUSer;
import com.example.todo.model.Todo;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
    List<Todo>findAllByUserId(AppUSer userId);
    List<Todo>findAllByUserIdAndCompleted(AppUSer userId,boolean completed);


}
