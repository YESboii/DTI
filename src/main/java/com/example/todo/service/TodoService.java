package com.example.todo.service;

import com.example.todo.model.AppUSer;
import com.example.todo.model.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoService{
    public Todo createTodo(Todo todo);
    public List<Todo> findAllTodos();
    public List<Todo> findAllTodos(AppUSer appUSer);
    public List<Todo> findAllTodos(AppUSer appUSer,boolean completed);

    public void deleteTodo(Todo todo);
    public void deleteTodo(int id);
    public Optional<Todo> findTodoById(int id);

    public Todo updateTodo(int id,Todo todo);
}
