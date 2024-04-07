package com.example.todo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "todo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String title;

    private String description;

    private String additionalDetails;
    private LocalDateTime reminderTime;

    private boolean completed;
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private AppUSer userId;
}
