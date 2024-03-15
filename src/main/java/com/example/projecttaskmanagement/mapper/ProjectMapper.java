package com.example.projecttaskmanagement.mapper;

import java.util.List;

import com.example.projecttaskmanagement.dto.ProjectDTO;
import com.example.projecttaskmanagement.entity.Project;

public interface ProjectMapper {
    
    ProjectDTO projectToDTO(Project project);
    Project dtoToProject(ProjectDTO projectDTO);
    List<ProjectDTO> projectListToDTOList(List<Project> projects);
   // List<Project> dtoToProjectList(List<ProjectDTO> projectDTOs);

}
