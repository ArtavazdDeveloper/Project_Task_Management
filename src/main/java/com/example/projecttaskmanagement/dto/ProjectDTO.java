package com.example.projecttaskmanagement.dto;

import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ProjectDTO {
    
    private int id;
    private String name;
    private String description;
    private List<TaskDTO> tasks;
    private List<UserDTO> users;
    
}
