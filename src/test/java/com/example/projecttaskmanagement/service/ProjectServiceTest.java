package com.example.projecttaskmanagement.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.example.projecttaskmanagement.dto.ProjectDTO;
import com.example.projecttaskmanagement.entity.Project;
import com.example.projecttaskmanagement.entity.User;
import com.example.projecttaskmanagement.mapper.ProjectMapper;
import com.example.projecttaskmanagement.repository.ProjectRepository;

public class ProjectServiceTest {

    private ProjectRepository projectRepository;
    private ProjectMapper projectMapper;
    private ProjectService projectService;

    @Before
    public void setUp() {
        projectRepository = mock(ProjectRepository.class);
        projectMapper = mock(ProjectMapper.class);
        projectService = new ProjectService(projectRepository, projectMapper);
    }

    @Test
    public void testGetAllProjects() {
        List<Project> projects = new ArrayList<>();
        when(projectRepository.findAll()).thenReturn(projects);
        when(projectMapper.projectListToDTOList(projects)).thenReturn(new ArrayList<>());
        List<ProjectDTO> result = projectService.getAllProjects();
        assertEquals(0, result.size());
    }

    @Test
    public void testGetProjectById() {
        int projectId = 1;
        Project project = new Project();
        when(projectRepository.findById(projectId)).thenReturn(project);
        when(projectMapper.projectToDTO(project)).thenReturn(new ProjectDTO());
        ProjectDTO result = projectService.getProjectById(projectId);
        assertNotNull(result);
    }

    @Test
    public void testCreateProject() {
        ProjectDTO projectDTO = new ProjectDTO();
        Project project = new Project();
        when(projectMapper.dtoToProject(projectDTO)).thenReturn(project);
        when(projectMapper.projectToDTO(project)).thenReturn(projectDTO);
        ProjectDTO result = projectService.createProject(projectDTO);
        assertNotNull(result);
    }

    @Test
    public void testUpdateProject() {
        int projectId = 1;
        ProjectDTO projectDTO = new ProjectDTO();
        Project existingProject = new Project();
        when(projectRepository.findById(projectId)).thenReturn(existingProject);
        when(projectMapper.dtoToProject(projectDTO)).thenReturn(existingProject);
        ProjectDTO result = projectService.updateProject(projectId, projectDTO);
        assertNotNull(result);
    }

    @Test
    public void testUpdateProject_NotFound() {
        int projectId = 1;
        ProjectDTO projectDTO = new ProjectDTO();
        when(projectRepository.findById(projectId)).thenReturn(null);
        ProjectDTO result = projectService.updateProject(projectId, projectDTO);
        assertNull(result);
    }

    @Test
    public void testDeleteProject() {
        int projectId = 1;
        projectService.deleteProject(projectId);
        verify(projectRepository, times(1)).delete(projectId);
    }

    @Test
    public void testUpdateProjectsByUserId() {
        int userId = 1;
        User updatedUser = new User();
        List<Project> projects = new ArrayList<>();
        when(projectRepository.findAllByUserId(userId)).thenReturn(projects);
        projectService.updateProjectsByUserId(userId, updatedUser);
        for (Project project : projects) {
            assertEquals(updatedUser, project.getUser());
            verify(projectRepository).update(project);
        }
    }

    @Test
    public void testDeleteProjectsByUserId() {
        int userId = 1;
        List<Project> projects = new ArrayList<>();
        when(projectRepository.findAllByUserId(userId)).thenReturn(projects);
        projectService.deleteProjectsByUserId(userId);
        for (Project project : projects) {
            verify(projectRepository).delete(project.getId());
        }
    }
}
