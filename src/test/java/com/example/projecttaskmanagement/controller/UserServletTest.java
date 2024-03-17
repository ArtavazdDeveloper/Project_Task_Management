package com.example.projecttaskmanagement.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

import com.example.projecttaskmanagement.controller.UserServlet;
import com.example.projecttaskmanagement.dto.UserDTO;
import com.example.projecttaskmanagement.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserServletTest {

    private UserServlet userServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private UserService userService;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        userService = mock(UserService.class);
        objectMapper = new ObjectMapper();
        userServlet = new UserServlet(userService, objectMapper);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
    }

    @Test
    public void testDoGet() throws Exception {
        List<UserDTO> users = new ArrayList<>();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setName("User");
        users.add(userDTO);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        when(userService.getAllUsers()).thenReturn(users);
        userServlet.doGet(request, response);
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        verify(response.getWriter()).write(objectMapper.writeValueAsString(users));
    }

    @Test
    public void testDoPost() throws Exception {
        String requestBody = "{\"id\":1,\"name\":\"User\"}";
        BufferedReader reader = new BufferedReader(new StringReader(requestBody));
        when(request.getReader()).thenReturn(reader);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setName("User");
        when(userService.createUser(userDTO)).thenReturn(userDTO);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        userServlet.doPost(request, response);
        verify(userService).createUser(userDTO);
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        verify(response.getWriter()).write(objectMapper.writeValueAsString(userDTO));
    }

    @Test
    public void testDoPut() throws Exception {
        String requestBody = "{\"id\":1,\"name\":\"User\"}";
        BufferedReader reader = new BufferedReader(new StringReader(requestBody));
        when(request.getReader()).thenReturn(reader);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setName("User");
        when(userService.updateUser(1, userDTO)).thenReturn(userDTO);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        userServlet.doPut(request, response);
        verify(userService).updateUser(1, userDTO);
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        verify(response.getWriter()).write(objectMapper.writeValueAsString(userDTO));
    }

    @Test
    public void testDoDelete() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(response.getWriter()).thenReturn(mock(PrintWriter.class));
        userServlet.doDelete(request, response);
        verify(userService).deleteUser(1);
        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
