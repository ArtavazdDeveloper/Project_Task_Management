package com.example.projecttaskmanagement.service;

import java.util.List;

import com.example.projecttaskmanagement.db.DBConnectionProvider;
import com.example.projecttaskmanagement.dto.ProjectDTO;
import com.example.projecttaskmanagement.dto.TaskDTO;
import com.example.projecttaskmanagement.entity.Project;
import com.example.projecttaskmanagement.entity.Task;
import com.example.projecttaskmanagement.entity.User;
import com.example.projecttaskmanagement.mapper.ProjectMapper;
import com.example.projecttaskmanagement.mapper.TaskMapper;
import com.example.projecttaskmanagement.mapper.impl.ProjectMapperImpl;
import com.example.projecttaskmanagement.mapper.impl.TaskMapperImpl;
import com.example.projecttaskmanagement.repository.TaskRepository;

import com.example.projecttaskmanagement.repository.impl.JdbcTaskRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TaskService {
  
    private final TaskRepository taskRepository = new JdbcTaskRepository(DBConnectionProvider.connectionDB());
    private final TaskMapper taskMapper = new TaskMapperImpl();
    private final ProjectService projectService = new ProjectService();
    private  final ProjectMapper projectMapper = new ProjectMapperImpl();




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

//    public TaskDTO createTaskForProject(int projectId, TaskDTO taskDTO) {
//        // Проверяем, существует ли проект с указанным идентификатором
//        ProjectDTO projectDTO = projectService.getProjectById(projectId);
//        if (projectDTO == null) {
//            // Если проект не существует, бросаем исключение или возвращаем null
//            return null; // Например
//        }
//
//        // Создаем новую задачу
//        Task task = new Task();
//        task.setName(taskDTO.getName());
//        task.setDescription(taskDTO.getDescription());
//        task.setCompleted(taskDTO.isCompleted());
//        // Устанавливаем проект для задачи
//        task.setProject(projectMapper.dtoToProject(projectDTO));
//
//        // Сохраняем задачу в репозитории
//        Task savedTask = taskRepository.save(task);
//
//        // Преобразуем сохраненную задачу обратно в DTO и возвращаем её
//        return taskMapper.(savedTask);
//    }

    public Project convertToProject(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setId(projectDTO.getId());        project.setName(projectDTO.getName());
        return project;
    }

    public void deleteTasksByUserId(int userId) {
        List<Task> tasks = taskRepository.findAllByUserId(userId);
        for (Task task : tasks) {
            taskRepository.delete(task.getId());
        }
    }
}
