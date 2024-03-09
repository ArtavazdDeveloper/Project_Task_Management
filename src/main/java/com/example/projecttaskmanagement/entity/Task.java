package com.example.projecttaskmanagement.entity;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Task {

    private int id;
    private String description;
    private LocalDate deadline;
    private boolean completed;
    private Project project;
    private List<User> assignees;
}
