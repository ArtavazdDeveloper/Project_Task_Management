package com.example.projecttaskmanagement.controller;
import com.example.projecttaskmanagement.mapper.ProjectMapper;
import com.example.projecttaskmanagement.repository.ProjectRepository;
import com.example.projecttaskmanagement.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import com.example.projecttaskmanagement.dto.ProjectDto;


@WebServlet("/projects")
public class ProjectServlet extends HttpServlet{

    private ProjectRepository projectRepository;
    private ProjectMapper projectMapper;
    private final ProjectService projectService = new ProjectService();
    private final ObjectMapper objectMapper = new ObjectMapper();



    public ProjectServlet() {

    }

    @Override

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ProjectDto> projects = projectService.getAllProjects();
        String projectsJson = objectMapper.writeValueAsString(projects);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(projectsJson);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProjectDto projectDTO = objectMapper.readValue(request.getReader(), ProjectDto.class);
        ProjectDto createdProject = projectService.createProject(projectDTO);
        String createdProjectJson = objectMapper.writeValueAsString(createdProject);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(createdProjectJson);
    }



    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        StringBuilder jsonBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBody.append(line);
        }
        ProjectDto projectDTO = objectMapper.readValue(jsonBody.toString(), ProjectDto.class);
        int projectId = projectDTO.getId();
        ProjectDto updatedProject = projectService.updateProject(projectId, projectDTO);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), updatedProject);
    }


    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int projectId = Integer.parseInt(request.getParameter("id"));
        projectService.deleteProject(projectId);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
