package com.example.projecttaskmanagement.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import com.example.projecttaskmanagement.db.DBConnectionProvider;
import com.example.projecttaskmanagement.dto.UserDTO;
import com.example.projecttaskmanagement.mapper.impl.UserMapperImpl;
import com.example.projecttaskmanagement.repository.UserRepository;
import com.example.projecttaskmanagement.repository.impl.JdbcUserRepository;
import com.example.projecttaskmanagement.service.ProjectService;
import com.example.projecttaskmanagement.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Objects;

@WebServlet("/users")
public class UserServlet extends HttpServlet{
    
    private final UserService userService = new UserService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserServlet() {

    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserDTO> users = userService.getAllUsers();

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
        UserDTO userDTO = objectMapper.readValue(jsonBody.toString(), UserDTO.class);
        UserDTO createdUser = userService.createUser(userDTO);
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
        UserDTO userDTO = objectMapper.readValue(jsonBody.toString(), UserDTO.class);
        int userId = userDTO.getId();
        UserDTO updatedUser = userService.updateUser(userId, userDTO);
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
