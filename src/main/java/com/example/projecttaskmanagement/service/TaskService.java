package com.example.projecttaskmanagement.service;

import java.util.List;

import com.example.projecttaskmanagement.dto.TaskDTO;
import com.example.projecttaskmanagement.entity.Task;
import com.example.projecttaskmanagement.entity.User;
import com.example.projecttaskmanagement.mapper.TaskMapper;
import com.example.projecttaskmanagement.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TaskService {
  
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return taskMapper.taskListToDTOList(tasks);
    }

    public TaskDTO getTaskById(int id) {
        Task task = taskRepository.findById(id);
        if (task == null) {
            return null;
        }
        return taskMapper.taskToDto(task);
    }

    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = taskMapper.dtoToTask(taskDTO);
        task = taskRepository.save(task);
        return taskMapper.taskToDto(task);
    }

    public TaskDTO updateTask(int id, TaskDTO taskDTO) {
        Task existingTask = taskRepository.findById(id);
        if (existingTask == null) {
            return null;
        }

        Task taskToUpdate = taskMapper.dtoToTask(taskDTO);
        taskToUpdate.setId(id);
        taskRepository.update(taskToUpdate);
        return taskDTO;
    }

    public void deleteTask(int id) {
        taskRepository.delete(id);
    }

    public List<TaskDTO> getTasksByProjectId(int projectId) {
        List<Task> tasks = taskRepository.findAllByProjectId(projectId);
        return taskMapper.taskListToDTOList(tasks);
    }

    public List<TaskDTO> getTasksByUserId(int userId) {
        List<Task> tasks = taskRepository.findAllByUserId(userId);
        return taskMapper.taskListToDTOList(tasks);
    }

    public void updateTasksByUserId(int userId, User updatedUser) {
        List<Task> tasks = taskRepository.findAllByUserId(userId);
        for (Task task : tasks) {
            task.setUser(updatedUser);
            taskRepository.update(task);
        }
    }

    public void deleteTasksByUserId(int userId) {
        List<Task> tasks = taskRepository.findAllByUserId(userId);
        for (Task task : tasks) {
            taskRepository.delete(task.getId());
        }
    }
}
