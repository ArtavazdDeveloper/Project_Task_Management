package com.example.projecttaskmanagement.dto;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    
    private int id;
    private String name;
    private String surname;
    private String email;
    private List<ProjectDTO> projects;
    private List<TaskDTO> tasks;

}
