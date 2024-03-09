package com.example.projecttaskmanagement.mapper;

import com.example.projecttaskmanagement.dto.ProjectDTO;
import com.example.projecttaskmanagement.entity.Project;

import java.util.List;



public interface ProjectMapper {
    
    ProjectDTO projectToDTO(Project project);
    Project dtoToProject(ProjectDTO projectDTO);
    List<ProjectDTO> projectListToDTOList(List<Project> projects);

}
