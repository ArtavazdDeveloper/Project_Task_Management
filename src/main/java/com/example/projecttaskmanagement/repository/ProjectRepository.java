package com.example.projecttaskmanagement.repository;

import com.example.projecttaskmanagement.entity.Project;
import com.example.projecttaskmanagement.entity.Task;

import java.util.List;


public interface ProjectRepository {
    Project findById(int id);
    List<Project> findAll();
    void save(Project project);
    void update(Project project);
    void delete(int id);
    List<Project> findAllByUserId(int userId);
    void addTaskToProject(int projectId, Task task);


}
