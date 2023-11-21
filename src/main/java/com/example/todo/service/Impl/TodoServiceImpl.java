package com.example.todo.service.Impl;

import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import com.example.todo.service.TodoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }
    @Override
    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    @Override
    public List<Todo> findAllTodos() {
        return todoRepository.findAll();
    }

    @Override
    public void deleteTodo(Todo todo) {
         todoRepository.delete(todo);
    }

    @Override
    public void deleteTodo(int id) {
        todoRepository.deleteById(id);
    }

    @Override
    public Optional<Todo> findTodoById(int id) {
        return todoRepository.findById(id);
    }

    @Override
    public Todo updateTodo(int id, Todo todo) {
        todo.setId(id);
        return todoRepository.save(todo);
    }
}
