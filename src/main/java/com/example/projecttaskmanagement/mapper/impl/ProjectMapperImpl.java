package com.example.projecttaskmanagement.mapper.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.example.projecttaskmanagement.dto.ProjectDto;
import com.example.projecttaskmanagement.entity.Project;
import com.example.projecttaskmanagement.mapper.ProjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProjectMapperImpl implements ProjectMapper{

    @Override
    public ProjectDto projectToDTO(Project project) {
        ProjectDto dto = new ProjectDto();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
        return dto;
    }

    @Override
    public Project dtoToProject(ProjectDto projectDTO) {
        Project project = new Project();
        project.setId(projectDTO.getId());
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        return project;
    }

    @Override
    public List<ProjectDto> projectListToDTOList(List<Project> projects) {
        return projects.stream().map(this::projectToDTO).collect(Collectors.toList());
    }
}




