package com.example.projecttaskmanagement.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    
    private int id;
    private String description;
    private String name;
    private boolean completed;
    private int projectId;
    private int userId;
   // private  ProjectDTO projectDTO;
    private List<UserDto> users;
}
