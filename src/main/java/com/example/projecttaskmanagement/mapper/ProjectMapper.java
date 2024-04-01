package com.example.projecttaskmanagement.mapper;

import java.util.List;

import com.example.projecttaskmanagement.dto.ProjectDto;
import com.example.projecttaskmanagement.entity.Project;

public interface ProjectMapper {
    
    ProjectDto projectToDTO(Project project);
    Project dtoToProject(ProjectDto projectDTO);
    List<ProjectDto> projectListToDTOList(List<Project> projects);
   // List<Project> dtoToProjectList(List<ProjectDTO> projectDTOs);

}
