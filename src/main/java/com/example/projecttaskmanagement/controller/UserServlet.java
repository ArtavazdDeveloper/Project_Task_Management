package com.example.projecttaskmanagement.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.example.projecttaskmanagement.db.DBConnectionProvider;
import com.example.projecttaskmanagement.dto.UserDTO;
import com.example.projecttaskmanagement.mapper.impl.UserMapperImpl;
import com.example.projecttaskmanagement.repository.impl.JdbcUserRepository;
import com.example.projecttaskmanagement.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/users")
public class UserServlet extends HttpServlet{
    
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public UserServlet() {
        this.userService = new UserService(new JdbcUserRepository(DBConnectionProvider.getConnection()), new UserMapperImpl(), null, null);
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserDTO> users = userService.getAllUsers();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), users);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        UserDTO userDTO = new UserDTO();
        userDTO.setName(name);
        userDTO.setSurname(surname);
        userDTO.setEmail(email);
        UserDTO createdUser = userService.createUser(userDTO);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), createdUser);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setName(name);
        userDTO.setSurname(surname);
        userDTO.setEmail(email);
        UserDTO updatedUser = userService.updateUser(userId,userDTO);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), updatedUser);
    }
}
