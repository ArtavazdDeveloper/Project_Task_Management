package com.example.projecttaskmanagement.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

import com.example.projecttaskmanagement.controller.TaskServlet;
import com.example.projecttaskmanagement.dto.TaskDTO;
import com.example.projecttaskmanagement.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TaskServletTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter stringWriter;
    private TaskService taskService;
    private ObjectMapper objectMapper;
    private TaskServlet taskServlet;

    @Before
    public void setUp() throws IOException {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        stringWriter = new StringWriter();
        taskService = mock(TaskService.class);
        objectMapper = new ObjectMapper();
        taskServlet = new TaskServlet(taskService, objectMapper);

        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        List<TaskDTO> tasks = new ArrayList<>();
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(1);
        taskDTO.setName("Task 1");
        tasks.add(taskDTO);
        when(taskService.getAllTasks()).thenReturn(tasks);
        taskServlet.doGet(request, response);
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        String result = stringWriter.toString().trim();
        String expected = objectMapper.writeValueAsString(tasks);
        assertEquals(expected, result);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        String json = "{\"name\":\"Task 1\",\"description\":\"Description 1\"}";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(json.getBytes())));
        when(request.getReader()).thenReturn(reader);
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setName("Task 1");
        taskDTO.setDescription("Description 1");
        when(taskService.createTask(any(TaskDTO.class))).thenReturn(taskDTO);
        taskServlet.doPost(request, response);
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        String result = stringWriter.toString().trim();
        String expected = objectMapper.writeValueAsString(taskDTO);
        assertEquals(expected, result);
    }

    @Test
    public void testDoPut() throws ServletException, IOException {
        int taskId = 1;
        String json = "{\"name\":\"Updated Task 1\",\"description\":\"Updated Description 1\"}";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(json.getBytes())));
        when(request.getParameter("id")).thenReturn(String.valueOf(taskId));
        when(request.getReader()).thenReturn(reader);
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(taskId);
        taskDTO.setName("Updated Task 1");
        taskDTO.setDescription("Updated Description 1");
        when(taskService.updateTask(taskId, taskDTO)).thenReturn(taskDTO);
        taskServlet.doPut(request, response);
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        String result = stringWriter.toString().trim();
        String expected = objectMapper.writeValueAsString(taskDTO);
        assertEquals(expected, result);
    }

    @Test
    public void testDoDelete() throws ServletException, IOException {
        int taskId = 1;
        when(request.getParameter("id")).thenReturn(String.valueOf(taskId));
        taskServlet.doDelete(request, response);
        verify(taskService).deleteTask(taskId);
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }
}

