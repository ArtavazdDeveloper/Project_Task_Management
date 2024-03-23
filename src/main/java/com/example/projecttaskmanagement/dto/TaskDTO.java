package com.example.projecttaskmanagement.dto;

import java.util.List;

import com.example.projecttaskmanagement.entity.Project;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TaskDTO {
    
    private int id;
    private String description;
    private String name;
    private boolean completed;
    private int projectId;
    private  ProjectDTO projectDTO;
    private List<UserDTO> users;
}
