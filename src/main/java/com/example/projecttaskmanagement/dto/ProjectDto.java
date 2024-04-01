package com.example.projecttaskmanagement.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    
    private int id;
    private String name;
    private String description;
    private List<TaskDto> tasks;
    private List<UserDto> users;
    
}
