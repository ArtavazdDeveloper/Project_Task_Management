package com.example.projecttaskmanagement.repository;

import com.example.projecttaskmanagement.entity.Task;

import java.util.List;

public interface TaskRepository {
    Task findById(int id);
    List<Task> findAllByProjectId(int projectId);
    List<Task> findAllByUserId(int userId);
    List<Task> findAll();
    Task save(Task task);
    void update(Task task);
    void delete(int id);
}
