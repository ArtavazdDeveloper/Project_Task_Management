package com.example.projecttaskmanagement.service;

import java.util.List;

import com.example.projecttaskmanagement.dto.ProjectDTO;
import com.example.projecttaskmanagement.entity.Project;
import com.example.projecttaskmanagement.entity.User;
import com.example.projecttaskmanagement.mapper.ProjectMapper;
import com.example.projecttaskmanagement.repository.ProjectRepository;

import lombok.AllArgsConstructor;





@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

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
            // Обработка случая, когда проект не найден
            return null;
        }

        Project projectToUpdate = projectMapper.dtoToProject(projectDTO);
        projectToUpdate.setId(id);
        projectRepository.update(projectToUpdate);
        projectRepository.update(projectToUpdate);
        return projectDTO;
    }

    public void deleteProject(int id) {
        projectRepository.delete(id);
    }

    public void updateProjectsByUserId(int userId, User updatedUser) {
        List<Project> projects = projectRepository.findAllByUserId(userId);
        for (Project project : projects) {
            project.setUser(updatedUser);
            projectRepository.update(project);
        }
    }

    public void deleteProjectsByUserId(int userId) {
        List<Project> projects = projectRepository.findAllByUserId(userId);
        for (Project project : projects) {
            projectRepository.delete(project.getId());
        }
    }
    
}
