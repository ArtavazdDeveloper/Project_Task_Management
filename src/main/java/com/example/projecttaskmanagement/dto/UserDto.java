package com.example.projecttaskmanagement.dto;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    
    private int id;
    private String name;
    private String surname;
    private String email;
    private List<ProjectDto> projects;
    private List<TaskDto> tasks;

}
