package com.example.projecttaskmanagement.service;

import java.util.List;

import javax.sql.DataSource;

import com.example.projecttaskmanagement.db.DBConnectionProvider;
import com.example.projecttaskmanagement.dto.ProjectDto;
import com.example.projecttaskmanagement.dto.TaskDto;
import com.example.projecttaskmanagement.dto.UserDto;
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




@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository = new JdbcProjectRepository(DBConnectionProvider.getDataSource());

    private ProjectMapper projectMapper = new ProjectMapperImpl();
    private final  UserMapper userMapper = new UserMapperImpl();


    public List<ProjectDto> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projectMapper.projectListToDTOList(projects);
    }

    public ProjectDto getProjectById(int id) {
        Project project = projectRepository.findById(id);
        return projectMapper.projectToDTO(project);
    }

    public ProjectDto createProject(ProjectDto projectDTO) {
        Project project = projectMapper.dtoToProject(projectDTO);
        projectRepository.save(project);
        return projectMapper.projectToDTO(project);
    }

    public ProjectDto updateProject(int id, ProjectDto projectDTO) {
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
    public void addTaskToProject(int projectId, TaskDto taskDTO) {
        Task task = new Task();
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setCompleted(taskDTO.isCompleted());
        projectRepository.addTaskToProject(projectId, task);
    }
    public void addUserToProject(int projectId, int userId, UserDto userDTO) {
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
