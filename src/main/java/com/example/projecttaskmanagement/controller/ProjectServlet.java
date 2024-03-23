package com.example.projecttaskmanagement.controller;
import com.example.projecttaskmanagement.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.projecttaskmanagement.db.DBConnectionProvider;
import com.example.projecttaskmanagement.dto.ProjectDTO;
import com.example.projecttaskmanagement.mapper.impl.ProjectMapperImpl;
import com.example.projecttaskmanagement.repository.impl.JdbcProjectRepository;
import com.example.projecttaskmanagement.service.ProjectService;


@WebServlet("/projects")
public class ProjectServlet extends HttpServlet{

    private final ProjectService projectService = new ProjectService();
    private final ObjectMapper objectMapper = new ObjectMapper();



    public ProjectServlet() {

    }

    @Override

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ProjectDTO> projects = projectService.getAllProjects();
        String projectsJson = objectMapper.writeValueAsString(projects);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(projectsJson);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProjectDTO projectDTO = objectMapper.readValue(request.getReader(), ProjectDTO.class);
        ProjectDTO createdProject = projectService.createProject(projectDTO);
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
        ProjectDTO projectDTO = objectMapper.readValue(jsonBody.toString(), ProjectDTO.class);
        int projectId = projectDTO.getId();
        ProjectDTO updatedProject = projectService.updateProject(projectId, projectDTO);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), updatedProject);
    }


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String projectIdParam = request.getParameter("id");
        if (projectIdParam == null || projectIdParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        int projectId = Integer.parseInt(projectIdParam);
        projectService.deleteProject(projectId);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Создаем ObjectMapper
        ObjectMapper mapper = new ObjectMapper();

        // Создаем JSON объект для ответа
        Map<String, String> jsonResponse = new HashMap<>();
        jsonResponse.put("message", "Project deleted!");

        // Преобразуем JSON объект в строку
        String jsonStr = mapper.writeValueAsString(jsonResponse);

        // Записываем JSON в тело ответа
        PrintWriter out = response.getWriter();
        out.print(jsonStr);
        out.flush();
    }
}
