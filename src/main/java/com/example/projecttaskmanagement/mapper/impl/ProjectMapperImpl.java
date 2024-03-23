package com.example.projecttaskmanagement.mapper.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.example.projecttaskmanagement.dto.ProjectDTO;
import com.example.projecttaskmanagement.entity.Project;
import com.example.projecttaskmanagement.mapper.ProjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProjectMapperImpl implements ProjectMapper{

    @Override
    public ProjectDTO projectToDTO(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
        return dto;
    }

    @Override
    public Project dtoToProject(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setId(projectDTO.getId());
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        return project;
    }

    @Override
    public List<ProjectDTO> projectListToDTOList(List<Project> projects) {
        return projects.stream().map(this::projectToDTO).collect(Collectors.toList());
    }
}




