package com.example.todo.controller;

import com.example.todo.model.AppUSer;
import com.example.todo.model.Todo;
import com.example.todo.repository.UserRepository;
import com.example.todo.service.TodoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/inboxmaster")
public class TodoController {
    private final TodoService todoService;
//    @Autowired
    private final UserRepository userRepository;

    public TodoController(TodoService todoService,  UserRepository userRepository) {
        this.todoService = todoService;
        this.userRepository = userRepository;
    }
    @RequestMapping(value = "/createtodo",method = RequestMethod.GET)
    public String getTodoCreatePage(Model model){
        model.addAttribute("todo",new Todo());
        return "createtodo";
    }

    @RequestMapping(path="/createtodo",method = RequestMethod.POST)
    public String createTodo(@ModelAttribute("todo") Todo todo){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AppUSer appUSer = userRepository.findByUsername(username).get();
        todo.setUserId(appUSer);
        todoService.createTodo(todo);
        return "redirect:/inboxmaster/viewtodos";
    }

    @RequestMapping(path="/viewtodos",method = RequestMethod.GET)
    public String viewAllTodos(Model model,@RequestParam(value = "filter", required = false) String filter){
        List<Todo> todoList;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AppUSer appUSer = userRepository.findByUsername(username).get();
        if(filter==null){
            todoList= todoService.findAllTodos(appUSer);
        } else if (filter.compareTo("completed")==0) {
            todoList = todoService.findAllTodos(appUSer,true);
        } else if (filter.compareTo("overdue")==0) {
            todoList= todoService.findAllTodos(appUSer);
            LocalDateTime current = LocalDateTime.now();
            List<Todo> overdueTodos = new ArrayList<>();

            for (Todo todo : todoList) {
                if (!todo.isCompleted() && todo.getReminderTime().isBefore(current)) {
                    overdueTodos.add(todo);
                }
            }
            todoList = overdueTodos;

        } else{
            todoList= todoService.findAllTodos(appUSer,false);
            LocalDateTime current = LocalDateTime.now();
            List<Todo> overdueTodos = new ArrayList<>();

            for (Todo todo : todoList) {
                if (!todo.isCompleted() && todo.getReminderTime().isAfter(current)) {
                    overdueTodos.add(todo);
                }
            }
            todoList = overdueTodos;
        }
        model.addAttribute("todos",todoList);
        model.addAttribute("size",todoList.size());
        System.out.println(todoList.size());
        return "alltodos";
    }
    @DeleteMapping(path = "/delete/{id}")
    public String deleteTodo(@PathVariable("id") int id){
        todoService.deleteTodo(id);
        return "redirect:/viewtodos";
    }
    @PostMapping(path = "/complete")
    public String updateTodo(@RequestParam("id")int id,Model model){
        Todo todoToBeUpdated = todoService.findTodoById(id).orElseThrow(
                ()->new RuntimeException());
        todoToBeUpdated.setCompleted(true);
        todoService.createTodo(todoToBeUpdated);
        return "redirect:/inboxmaster/viewtodos";
    }
}
