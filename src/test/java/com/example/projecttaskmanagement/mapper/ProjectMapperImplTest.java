package com.example.projecttaskmanagement.mapper;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.example.projecttaskmanagement.dto.ProjectDTO;
import com.example.projecttaskmanagement.entity.Project;
import com.example.projecttaskmanagement.mapper.ProjectMapper;
import com.example.projecttaskmanagement.mapper.impl.ProjectMapperImpl;

public class ProjectMapperImplTest {

    private ProjectMapper projectMapper;

    @Before
    public void setUp() {
        projectMapper = new ProjectMapperImpl();
    }

    @Test
    public void testProjectToDto() {
        Project project = new Project();
        project.setId(1);
        project.setName("Project 1");
        project.setDescription("Description for Project 1");
        project.setStartDate(LocalDate.of(2024, 3, 31));
        project.setEndDate(LocalDate.of(2024, 4, 15));
        ProjectDTO projectDTO = projectMapper.projectToDTO(project);
        assertEquals(project.getId(), projectDTO.getId());
        assertEquals(project.getName(), projectDTO.getName());
        assertEquals(project.getDescription(), projectDTO.getDescription());
        assertEquals(project.getStartDate(), projectDTO.getStartDate());
        assertEquals(project.getEndDate(), projectDTO.getEndDate());
    }

    @Test
    public void testDtoToProject() {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(1);
        projectDTO.setName("Project 1");
        projectDTO.setDescription("Description for Project 1");
        projectDTO.setStartDate(LocalDate.of(2024, 3, 31));
        projectDTO.setEndDate(LocalDate.of(2024, 4, 15));
        Project project = projectMapper.dtoToProject(projectDTO);
        assertEquals(projectDTO.getId(), project.getId());
        assertEquals(projectDTO.getName(), project.getName());
        assertEquals(projectDTO.getDescription(), project.getDescription());
        assertEquals(projectDTO.getStartDate(), project.getStartDate());
        assertEquals(projectDTO.getEndDate(), project.getEndDate());
    }

    @Test
    public void testProjectListToDTOList() {
        List<Project> projects = new ArrayList<>();
        Project project1 = new Project();
        project1.setId(1);
        project1.setName("Project 1");
        project1.setDescription("Description for Project 1");
        project1.setStartDate(LocalDate.of(2024, 3, 31));
        project1.setEndDate(LocalDate.of(2024, 4, 15));
        projects.add(project1);
        Project project2 = new Project();
        project2.setId(2);
        project2.setName("Project 2");
        project2.setDescription("Description for Project 2");
        project2.setStartDate(LocalDate.of(2024, 4, 1));
        project2.setEndDate(LocalDate.of(2024, 4, 20));
        projects.add(project2);
        List<ProjectDTO> projectDTOs = projectMapper.projectListToDTOList(projects);
        assertEquals(projects.size(), projectDTOs.size());
        for (int i = 0; i < projects.size(); i++) {
            Project project = projects.get(i);
            ProjectDTO projectDTO = projectDTOs.get(i);
            assertEquals(project.getId(), projectDTO.getId());
            assertEquals(project.getName(), projectDTO.getName());
            assertEquals(project.getDescription(), projectDTO.getDescription());
            assertEquals(project.getStartDate(), projectDTO.getStartDate());
            assertEquals(project.getEndDate(), projectDTO.getEndDate());
        }
    }
}
