package com.example.projecttaskmanagement.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

import com.example.projecttaskmanagement.controller.ProjectServlet;
import com.example.projecttaskmanagement.dto.ProjectDTO;
import com.example.projecttaskmanagement.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProjectServletTest {

    private ProjectServlet projectServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ProjectService projectService;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        projectService = mock(ProjectService.class);
        objectMapper = new ObjectMapper();
        projectServlet = new ProjectServlet(projectService, objectMapper);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
    }

    @Test
    public void testDoGetWithProjectId() throws Exception {
        int projectId = 1;
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(projectId);
        projectDTO.setName("Project");
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        when(request.getParameter("id")).thenReturn(String.valueOf(projectId));
        when(projectService.getProjectById(projectId)).thenReturn(projectDTO);
        projectServlet.doGet(request, response);
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        verify(response.getWriter()).write(objectMapper.writeValueAsString(projectDTO));
    }

    @Test
    public void testDoGetWithoutProjectId() throws Exception {
        List<ProjectDTO> projects = new ArrayList<>();
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(1);
        projectDTO.setName("Project");
        projects.add(projectDTO);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        when(projectService.getAllProjects()).thenReturn(projects);
        projectServlet.doGet(request, response);
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        verify(response.getWriter()).write(objectMapper.writeValueAsString(projects));
    }

    @Test
    public void testDoPost() throws Exception {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("Project");
        projectDTO.setDescription("Description");
        projectDTO.setStartDate(LocalDate.now());
        projectDTO.setEndDate(LocalDate.now().plusDays(10));
        when(request.getParameter("name")).thenReturn("Project");
        when(request.getParameter("description")).thenReturn("Description");
        when(request.getParameter("startDate")).thenReturn(LocalDate.now().toString());
        when(request.getParameter("endDate")).thenReturn(LocalDate.now().plusDays(10).toString());
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        projectServlet.doPost(request, response);
        verify(projectService).createProject(projectDTO);
    }

    @Test
    public void testDoPut() throws Exception {
        int projectId = 1;
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(projectId);
        projectDTO.setName("Project");
        projectDTO.setDescription("Description");
        projectDTO.setStartDate(LocalDate.now());
        projectDTO.setEndDate(LocalDate.now().plusDays(10));
        when(request.getParameter("id")).thenReturn(String.valueOf(projectId));
        when(request.getParameter("name")).thenReturn("Project");
        when(request.getParameter("description")).thenReturn("Description");
        when(request.getParameter("startDate")).thenReturn(LocalDate.now().toString());
        when(request.getParameter("endDate")).thenReturn(LocalDate.now().plusDays(10).toString());
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        projectServlet.doPut(request, response);
        verify(projectService).updateProject(projectId, projectDTO);
    }

    @Test
    public void testDoDelete() throws Exception {
        int projectId = 1;
        when(request.getParameter("id")).thenReturn(String.valueOf(projectId));
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        projectServlet.doDelete(request, response);
        verify(projectService).deleteProject(projectId);
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }
}