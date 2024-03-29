package com.example.projecttaskmanagement.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.projecttaskmanagement.dto.TaskDTO;
import com.example.projecttaskmanagement.mapper.ProjectMapper;
import com.example.projecttaskmanagement.mapper.impl.ProjectMapperImpl;
import com.example.projecttaskmanagement.repository.ProjectRepository;
import com.example.projecttaskmanagement.service.ProjectService;
import com.example.projecttaskmanagement.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/task")
public class TaskServlet extends HttpServlet{
    private final TaskService taskService = new TaskService();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private ProjectRepository projectRepository;
    private final ProjectService projectService = new ProjectService();
    private final ProjectMapper projectMapper = new ProjectMapperImpl();

    public TaskServlet() {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<TaskDTO> tasks = taskService.getAllTasks();
        String tasksJson = objectMapper.writeValueAsString(tasks);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(tasksJson);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TaskDTO taskDTO = objectMapper.readValue(request.getReader(), TaskDTO.class);
        int projectId = taskDTO.getProjectId();
        int userId = taskDTO.getUserId();
        TaskDTO createdTask = taskService.createTaskForProject(projectId, userId, taskDTO);
        String createdTaskJson = objectMapper.writeValueAsString(createdTask);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(createdTaskJson);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] parts = request.getPathInfo().split("/");
        if (parts.length < 2) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        int taskId;
        try {
            taskId = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        TaskDTO taskDTO = objectMapper.readValue(request.getReader(), TaskDTO.class);
        TaskDTO updatedTask = taskService.updateTask(taskId, taskDTO);
        if (updatedTask == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            String updatedTaskJson = objectMapper.writeValueAsString(updatedTask);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(updatedTaskJson);
        }
    }



    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int taskId = Integer.parseInt(request.getParameter("id"));
        taskService.deleteTask(taskId);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}

