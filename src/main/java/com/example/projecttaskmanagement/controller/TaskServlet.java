package com.example.projecttaskmanagement.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.projecttaskmanagement.dto.TaskDto;
import com.example.projecttaskmanagement.mapper.ProjectMapper;
import com.example.projecttaskmanagement.mapper.TaskMapper;
import com.example.projecttaskmanagement.mapper.UserMapper;
import com.example.projecttaskmanagement.mapper.impl.ProjectMapperImpl;
import com.example.projecttaskmanagement.mapper.impl.TaskMapperImpl;
import com.example.projecttaskmanagement.mapper.impl.UserMapperImpl;
import com.example.projecttaskmanagement.repository.ProjectRepository;
import com.example.projecttaskmanagement.service.ProjectService;
import com.example.projecttaskmanagement.service.TaskService;
import com.example.projecttaskmanagement.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/task")
public class TaskServlet extends HttpServlet{
    
    private TaskService taskService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        super.init();
        ProjectService projectService = new ProjectService();
        UserService userService = new UserService(taskService, null, null, projectService);
        TaskMapper taskMapper = new TaskMapperImpl();
        UserMapper userMapper = new UserMapperImpl();
        ProjectMapper projectMapper = new ProjectMapperImpl();
        this.taskService = new TaskService(projectService, userService, projectMapper, userMapper);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<TaskDto> tasks = taskService.getAllTasks();
        String tasksJson = objectMapper.writeValueAsString(tasks);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(tasksJson);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TaskDto taskDTO = objectMapper.readValue(request.getReader(), TaskDto.class);
        int projectId = taskDTO.getProjectId();
        int userId = taskDTO.getUserId();
        TaskDto createdTask = taskService.createTaskForProject(projectId, userId, taskDTO);
        String createdTaskJson = objectMapper.writeValueAsString(createdTask);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(createdTaskJson);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        StringBuilder jsonBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBody.append(line);
        }
        TaskDto taskDTO = objectMapper.readValue(jsonBody.toString(), TaskDto.class);
        int taskId = taskDTO.getId(); // Предположим, что в объекте TaskDto есть метод getId() для получения идентификатора задачи
        TaskDto updatedTask = taskService.updateTask(taskId, taskDTO);
        if (updatedTask == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // Если задача не найдена, установите статус 404
        } else {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            objectMapper.writeValue(response.getWriter(), updatedTask);
        }
    }


    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int taskId = Integer.parseInt(request.getParameter("id"));
        taskService.deleteTask(taskId);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}

