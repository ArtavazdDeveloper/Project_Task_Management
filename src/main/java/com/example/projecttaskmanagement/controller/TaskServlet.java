package com.example.projecttaskmanagement.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.projecttaskmanagement.dto.TaskDTO;
import com.example.projecttaskmanagement.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/task")
public class TaskServlet extends HttpServlet{
    private final TaskService taskService;
    private final ObjectMapper objectMapper;

    public TaskServlet(TaskService taskService, ObjectMapper objectMapper) {
        this.taskService = taskService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<TaskDTO> tasks = taskService.getAllTasks();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), tasks);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setName(name);
        taskDTO.setDescription(description);
        TaskDTO createdTask = taskService.createTask(taskDTO);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), createdTask);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int taskId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(taskId);
        taskDTO.setName(name);
        taskDTO.setDescription(description);
        TaskDTO updatedTask = taskService.updateTask(taskId, taskDTO);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), updatedTask);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int taskId = Integer.parseInt(request.getParameter("id"));
        taskService.deleteTask(taskId);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
