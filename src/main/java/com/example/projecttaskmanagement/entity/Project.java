package com.example.projecttaskmanagement.entity;

import java.time.LocalDate;
import java.util.List;
  

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Project {
  
    private int id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Task task;
    private List<Task> tasks;
    private User user;
    private List<User> users;


}
