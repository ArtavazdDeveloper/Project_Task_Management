package com.example.projecttaskmanagement.service;

import java.util.List;

import com.example.projecttaskmanagement.db.DBConnectionProvider;
import com.example.projecttaskmanagement.dto.ProjectDto;
import com.example.projecttaskmanagement.dto.TaskDto;
import com.example.projecttaskmanagement.dto.UserDto;
import com.example.projecttaskmanagement.entity.Project;
import com.example.projecttaskmanagement.entity.Task;
import com.example.projecttaskmanagement.entity.User;
import com.example.projecttaskmanagement.mapper.ProjectMapper;
import com.example.projecttaskmanagement.mapper.TaskMapper;
import com.example.projecttaskmanagement.mapper.UserMapper;
import com.example.projecttaskmanagement.mapper.impl.ProjectMapperImpl;
import com.example.projecttaskmanagement.mapper.impl.TaskMapperImpl;
import com.example.projecttaskmanagement.mapper.impl.UserMapperImpl;
import com.example.projecttaskmanagement.repository.TaskRepository;

import com.example.projecttaskmanagement.repository.impl.JdbcTaskRepository;
import lombok.RequiredArgsConstructor;


public class TaskService {
  
    private final TaskRepository taskRepository = new JdbcTaskRepository(DBConnectionProvider.getDataSource());
    private final TaskMapper taskMapper = new TaskMapperImpl();
    private final ProjectService projectService;
    private final UserService userService;
    private final ProjectMapper projectMapper;
    private final UserMapper userMapper;

    public TaskService(ProjectService projectService, UserService userService, ProjectMapper projectMapper, UserMapper userMapper) {
        this.projectService = projectService;
        this.userService = userService;
        this.projectMapper = projectMapper;
        this.userMapper = userMapper;
    }



    public List<TaskDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return taskMapper.taskListToDTOList(tasks);
    }

    public TaskDto getTaskById(int id) {
        Task task = taskRepository.findById(id);
        if (task == null) {
            return null;
        }
        return taskMapper.taskToDto(task);
    }

    public TaskDto createTask(TaskDto taskDTO) {
        Task task = taskMapper.dtoToTask(taskDTO);
        task = taskRepository.save(task);
        return taskMapper.taskToDto(task);
    }

    public TaskDto updateTask(int taskId, TaskDto taskDTO) {
        Task existingTask = taskRepository.findById(taskId);
        if (existingTask == null) {
            return null;
        }
        if (taskDTO.getName() != null) {
            existingTask.setName(taskDTO.getName());
        }
        if (taskDTO.getDescription() != null) {
            existingTask.setDescription(taskDTO.getDescription());
        }
        if (taskDTO.isCompleted() != false) {
            existingTask.setCompleted(taskDTO.isCompleted());
        }
        if (taskDTO.getUserId() != 0) {
            existingTask.setUser_id(taskDTO.getUserId());
        }
        if(taskDTO.getProjectId() != 0){
            existingTask.setProject_id(taskDTO.getProjectId());
        }
        taskRepository.update(existingTask);
        return taskMapper.taskToDto(existingTask);
    }

    public void deleteTask(int id) {
        taskRepository.delete(id);
    }

    public List<TaskDto> getTasksByProjectId(int projectId) {
        List<Task> tasks = taskRepository.findAllByProjectId(projectId);
        return taskMapper.taskListToDTOList(tasks);
    }

    public List<TaskDto> getTasksByUserId(int userId) {
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
    public TaskDto createTaskForProject(int projectId, int userId, TaskDto taskDTO) {
        ProjectDto projectDTO = projectService.getProjectById(projectId);
        if (projectDTO == null) {
            return null;
        }
        UserDto userDTO = userService.getUserById(userId);
        if (userDTO == null){
            return null;
        }
        Task task = taskMapper.dtoToTask(taskDTO);
        task.setProject(projectMapper.dtoToProject(projectDTO));
        task.setUser(userMapper.dtoToUser(userDTO));
        Task savedTask = taskRepository.save(task);
        return taskMapper.taskToDto(savedTask);
    }


    public Project convertToProject(ProjectDto projectDTO) {
        Project project = new Project();
        project.setId(projectDTO.getId());
        project.setName(projectDTO.getName());
        return project;
    }

    public void deleteTasksByUserId(int userId) {
        List<Task> tasks = taskRepository.findAllByUserId(userId);
        for (Task task : tasks) {
            taskRepository.delete(task.getId());
        }
    }
}
