package com.example.projecttaskmanagement.service;

import java.sql.SQLException;
import java.util.List;

import com.example.projecttaskmanagement.db.DBConnectionProvider;
import com.example.projecttaskmanagement.dto.ProjectDTO;
import com.example.projecttaskmanagement.dto.TaskDTO;
import com.example.projecttaskmanagement.dto.UserDTO;
import com.example.projecttaskmanagement.entity.Project;
import com.example.projecttaskmanagement.entity.Task;
import com.example.projecttaskmanagement.entity.User;
import com.example.projecttaskmanagement.mapper.ProjectMapper;
import com.example.projecttaskmanagement.mapper.UserMapper;
import com.example.projecttaskmanagement.mapper.impl.ProjectMapperImpl;
import com.example.projecttaskmanagement.mapper.impl.UserMapperImpl;
import com.example.projecttaskmanagement.repository.ProjectRepository;

import com.example.projecttaskmanagement.repository.impl.JdbcProjectRepository;
import lombok.RequiredArgsConstructor;
import org.mariadb.jdbc.Connection;


@RequiredArgsConstructor
public class ProjectService {

    private ProjectRepository projectRepository = new JdbcProjectRepository(DBConnectionProvider.connectionDB());

    private ProjectMapper projectMapper = new ProjectMapperImpl();
    private final  UserMapper userMapper = new UserMapperImpl();



    public List<ProjectDTO> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projectMapper.projectListToDTOList(projects);
    }

    public ProjectDTO getProjectById(int id) {
        Project project = projectRepository.findById(id);
        return projectMapper.projectToDTO(project);
    }

    public ProjectDTO createProject(ProjectDTO projectDTO) {
        Project project = projectMapper.dtoToProject(projectDTO);
        projectRepository.save(project);
        return projectMapper.projectToDTO(project);
    }

    public ProjectDTO updateProject(int id, ProjectDTO projectDTO) {
        Project existingProject = projectRepository.findById(id);
        if (existingProject == null) {
            return null;
        }

        Project projectToUpdate = projectMapper.dtoToProject(projectDTO);
        projectToUpdate.setId(id);
        projectRepository.update(projectToUpdate);

        // Получить обновленный проект из репозитория
        Project updatedProject = projectRepository.findById(id);

        // Сопоставить обновленный проект с помощью projectMapper
        return projectMapper.projectToDTO(updatedProject);
    }



    public void deleteProject(int id) {
        projectRepository.delete(id);
    }

    public void updateProjectsByUserId(int userId, User updatedUser) {
        List<Project> projects = projectRepository.findAllByUserId(userId);
        for (Project project : projects) {
            List<User> users = project.getUsers();
            users.add(updatedUser);
            project.setUsers(users);
            projectRepository.update(project);
        }
    }
    public void addTaskToProject(int projectId, TaskDTO taskDTO) {
        Task task = new Task();
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setCompleted(taskDTO.getCompleted());
        projectRepository.addTaskToProject(projectId, task);
    }
    public void addUserToProject(int projectId, int userId, UserDTO userDTO) {
        Project project = projectRepository.findById(projectId);
        if (project == null) {
            return;
        }
        User user = userMapper.dtoToUser(userDTO);
        user.setId(userId);
        List<User> users = project.getUsers();
        users.add(user);
        project.setUsers(users);
        projectRepository.update(project);
    }


    public void deleteProjectsByUserId(int userId) {
        List<Project> projects = projectRepository.findAllByUserId(userId);
        for (Project project : projects) {
            projectRepository.delete(project.getId());
        }
    }
    
}
