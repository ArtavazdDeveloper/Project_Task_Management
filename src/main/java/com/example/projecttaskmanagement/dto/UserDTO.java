package com.example.projecttaskmanagement.dto;

import com.example.projecttaskmanagement.entity.Project;
import com.example.projecttaskmanagement.entity.Task;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Data
@RequiredArgsConstructor
public class UserDTO {
    
    private  int id;
    private String name;
    private String surname;
    private String email;
    private List<Project> projects;
    private List<Task> tasks;
}
