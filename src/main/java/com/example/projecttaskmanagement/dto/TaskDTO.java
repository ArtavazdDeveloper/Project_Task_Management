package com.example.projecttaskmanagement.dto;

import java.time.LocalDate;
import java.util.List;

import com.example.projecttaskmanagement.entity.Project;
import com.example.projecttaskmanagement.entity.User;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TaskDTO {
    
    private int id;
    private String description;
    private String name;
    private LocalDate deadline;
    private boolean completed;
    private Project project;
    User user;
    private List<UserDTO> assignees;

    
}
