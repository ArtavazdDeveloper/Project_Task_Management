package com.example.projecttaskmanagement.controller;
import com.example.projecttaskmanagement.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.example.projecttaskmanagement.db.DBConnectionProvider;
import com.example.projecttaskmanagement.dto.ProjectDTO;
import com.example.projecttaskmanagement.mapper.impl.ProjectMapperImpl;
import com.example.projecttaskmanagement.repository.impl.JdbcProjectRepository;
import com.example.projecttaskmanagement.service.ProjectService;


@WebServlet("/projects")
public class ProjectServlet extends HttpServlet{
    
    private ProjectService projectService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        super.init();
        projectService = new ProjectService(new JdbcProjectRepository((DBConnectionProvider) DBConnectionProvider.getConnection()), new ProjectMapperImpl());
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String projectIdParam = request.getParameter("id");
        if (projectIdParam != null) {
            int projectId = Integer.parseInt(projectIdParam);
            ProjectDTO projectDTO = projectService.getProjectById(projectId);
            if (projectDTO != null) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                objectMapper.writeValue(response.getWriter(), projectDTO);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Project not found");
            }
        } else {
            List<ProjectDTO> projects = projectService.getAllProjects();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            objectMapper.writeValue(response.getWriter(), projects);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
        LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName(name);
        projectDTO.setDescription(description);
        projectDTO.setStartDate(startDate);
        projectDTO.setEndDate(endDate);
        ProjectDTO createdProject = projectService.createProject(projectDTO);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), createdProject);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    int projectId = Integer.parseInt(request.getParameter("id")); // Получаем идентификатор проекта из параметра запроса
    String name = request.getParameter("name");
    String description = request.getParameter("description");
    LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
    LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));
    ProjectDTO projectDTO = new ProjectDTO();
    projectDTO.setId(projectId);
    projectDTO.setName(name);
    projectDTO.setDescription(description);
    projectDTO.setStartDate(startDate);
    projectDTO.setEndDate(endDate);
    ProjectDTO updatedProject = projectService.updateProject(projectId, projectDTO);
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    objectMapper.writeValue(response.getWriter(), updatedProject);

    }
}
