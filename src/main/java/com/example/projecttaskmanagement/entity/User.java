package com.example.projecttaskmanagement.entity;

import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
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
