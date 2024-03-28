package com.example.projecttaskmanagement.dto;

import java.util.List;

import com.example.projecttaskmanagement.entity.Project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    
    private int id;
    private String description;
    private String name;
    private Boolean completed;
    private int projectId;
    private int userId;
   // private  ProjectDTO projectDTO;
    private List<UserDTO> users;
}
