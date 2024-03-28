package com.example.projecttaskmanagement.service;

import java.util.List;

import com.example.projecttaskmanagement.db.DBConnectionProvider;
import com.example.projecttaskmanagement.dto.ProjectDTO;
import com.example.projecttaskmanagement.dto.TaskDTO;
import com.example.projecttaskmanagement.dto.UserDTO;
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

@RequiredArgsConstructor
public class TaskService {
  
    private final TaskRepository taskRepository = new JdbcTaskRepository(DBConnectionProvider.connectionDB());

    private final TaskMapper taskMapper = new TaskMapperImpl();
    private final ProjectService projectService = new ProjectService();
    private  final UserService userService = new UserService();
    private  final ProjectMapper projectMapper = new ProjectMapperImpl();
    private  final UserMapper userMapper = new UserMapperImpl();




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

    public TaskDTO updateTask(int taskId, TaskDTO taskDTO) {
        Task existingTask = taskRepository.findById(taskId);
        if (existingTask == null) {
            return null; // Задача не найдена, возвращаем null
        }

        // Обновляем поля задачи
        if (taskDTO.getName() != null) {
            existingTask.setName(taskDTO.getName());
        }
        if (taskDTO.getDescription() != null) {
            existingTask.setDescription(taskDTO.getDescription());
        }
        if (taskDTO.getCompleted() != null) {
            existingTask.setCompleted(taskDTO.getCompleted());
        }

        // Сохраняем обновленную задачу
        taskRepository.update(existingTask);

        // Возвращаем обновленную задачу
        return taskMapper.taskToDto(existingTask);
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
    public TaskDTO createTaskForProject(int projectId, int userId, TaskDTO taskDTO) {
        ProjectDTO projectDTO = projectService.getProjectById(projectId);
        if (projectDTO == null) {
            return null;
        }
        UserDTO userDTO = userService.getUserById(userId);
        if (userDTO == null){
            return null;
        }
        Task task = taskMapper.dtoToTask(taskDTO);
        task.setProject(projectMapper.dtoToProject(projectDTO));
        task.setUser(userMapper.dtoToUser(userDTO));
        Task savedTask = taskRepository.save(task);
        return taskMapper.taskToDto(savedTask);
    }


    public Project convertToProject(ProjectDTO projectDTO) {
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
