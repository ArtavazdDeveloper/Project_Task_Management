package com.example.projecttaskmanagement.dto;

import java.util.List;

import com.example.projecttaskmanagement.entity.Project;
import com.example.projecttaskmanagement.entity.Task;


import lombok.Data;
import lombok.RequiredArgsConstructor;
@Data
@RequiredArgsConstructor
public class UserDTO {
    
    private int id;
    private String name;
    private String surname;
    private String email;
    private List<ProjectDTO> projectDTOS;
    private List<TaskDTO> tasks;

}
