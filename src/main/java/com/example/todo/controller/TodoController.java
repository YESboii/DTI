package com.example.todo.controller;

import com.example.todo.model.Todo;
import com.example.todo.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }
    @RequestMapping("/createtodo")
    public String getTodoCreatePage(Model model){
        model.addAttribute("todo",new Todo());
        return "createtodo";
    }

    @RequestMapping(path="/createtodo",method = RequestMethod.POST)
    public String createTodo(@ModelAttribute("todo") Todo todo){
        todoService.createTodo(todo);
        return "redirect:/viewtodos";
    }

    @RequestMapping(path="/viewtodos",method = RequestMethod.GET)
    public String viewAllTodos(Model model){
        List<Todo> todoList = todoService.findAllTodos();
        model.addAttribute("todos",todoList);
        return "viewtodos";
    }
    @DeleteMapping(path = "/delete/{id}")
    public String deleteTodo(@PathVariable("id") int id){
        todoService.deleteTodo(id);
        return "redirect:/viewtodos";
    }
    @PutMapping(path = "/update/{id}")
    public String updateTodo(@PathVariable("id")int id,Model model){
        Todo todoToBeUpdated = todoService.findTodoById(id).orElseThrow(
                ()->new RuntimeException());
        model.addAttribute("todo",todoToBeUpdated);
        System.out.println(todoToBeUpdated.getId());
        return "createtodo";
    }
}
