package com.example.projecttaskmanagement.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    private int id;
    private String name;
    private String surname;
    private String email;
    private Project project;
    private List<Project> projects;
    private Task task;
    private List<Task> tasks;
}
