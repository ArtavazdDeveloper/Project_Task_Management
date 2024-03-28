package com.example.projecttaskmanagement.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    
    private int id;
    private String name;
    private String description;
    private List<TaskDTO> tasks;
    private List<UserDTO> users;
    
}
