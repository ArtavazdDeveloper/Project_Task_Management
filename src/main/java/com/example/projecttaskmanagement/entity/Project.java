package com.example.projecttaskmanagement.entity;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    private int id;
    private String name;
    private String description;
    private List<Task> tasks;
    private List<User> users;
}
