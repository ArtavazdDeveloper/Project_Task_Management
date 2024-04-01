package com.example.projecttaskmanagement.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    private int id;
    private String name;
    private String description;
    private boolean completed;
    private int project_id;
    private int user_id;
    private Project project;
    private List<Project> projects;
    private User user;
    private List<User> assignees;
    
}