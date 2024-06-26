package com.example.projecttaskmanagement.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import com.example.projecttaskmanagement.dto.UserDto;
import com.example.projecttaskmanagement.mapper.ProjectMapper;
import com.example.projecttaskmanagement.repository.ProjectRepository;
import com.example.projecttaskmanagement.service.ProjectService;
import com.example.projecttaskmanagement.service.TaskService;
import com.example.projecttaskmanagement.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/users")
public class UserServlet extends HttpServlet{

    private TaskService taskService;
    private UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        super.init();
        // Инициализируем зависимости в методе init
        this.taskService = new TaskService(null, userService, null, null);
        this.userService = new UserService(taskService, null, null, null);
    }

    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserDto> users = userService.getAllUsers();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), users);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        StringBuilder jsonBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBody.append(line);
        }
        UserDto userDTO = objectMapper.readValue(jsonBody.toString(), UserDto.class);
        UserDto createdUser = userService.createUser(userDTO);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), createdUser);
    }
    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        StringBuilder jsonBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBody.append(line);
        }
        UserDto userDTO = objectMapper.readValue(jsonBody.toString(), UserDto.class);
        int userId = userDTO.getId();
        UserDto updatedUser = userService.updateUser(userId, userDTO);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), updatedUser);
    }


    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        userService.deleteUser(userId);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
