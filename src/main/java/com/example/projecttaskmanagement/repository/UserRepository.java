package com.example.projecttaskmanagement.repository;

import com.example.projecttaskmanagement.entity.User;

import java.util.List;

public interface UserRepository {
    User findById(int id);
    List<User> findAll();
    void save(User user);
    void update(User user);
    void delete(int id);
    List<User> findAllByTaskId(int taskId);
}
