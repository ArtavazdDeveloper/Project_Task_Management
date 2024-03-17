package com.example.projecttaskmanagement.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import com.example.projecttaskmanagement.entity.Project;
import com.example.projecttaskmanagement.entity.Task;
import com.example.projecttaskmanagement.repository.impl.JdbcTaskRepository;

public class JdbcTaskRepositoryTest {
    private Connection connection;
    private JdbcTaskRepository jdbcTaskRepository;

    @Before
    public void setUp() {
        connection = mock(Connection.class);
        jdbcTaskRepository = new JdbcTaskRepository(connection);
    }

    @Test
    public void testFindById() throws SQLException {
        int taskId = 1;
        String description = "Test Task";
        LocalDate deadline = LocalDate.of(2022, 1, 1);
        boolean completed = false;
        int projectId = 1;
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(anyString())).thenReturn(mock(PreparedStatement.class));
        when(connection.prepareStatement(anyString()).executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(taskId);
        when(resultSet.getString("description")).thenReturn(description);
        when(resultSet.getDate("deadline")).thenReturn(java.sql.Date.valueOf(deadline));
        when(resultSet.getBoolean("completed")).thenReturn(completed);
        when(resultSet.getInt("project_id")).thenReturn(projectId);
        Task task = jdbcTaskRepository.findById(taskId);
        assertEquals(taskId, task.getId());
        assertEquals(description, task.getDescription());
        assertEquals(deadline, task.getDeadline());
        assertEquals(completed, task.isCompleted());
        assertEquals(projectId, task.getProject().getId());
    }

    @Test
    public void testFindAllByUserId() throws SQLException {
        int userId = 1;
        int taskId1 = 1;
        int taskId2 = 2;
        String description = "Test Task";
        LocalDate deadline = LocalDate.of(2022, 1, 1);
        boolean completed = false;
        int projectId = 1;
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(anyString())).thenReturn(mock(PreparedStatement.class));
        when(connection.prepareStatement(anyString()).executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(taskId1).thenReturn(taskId2);
        when(resultSet.getString("description")).thenReturn(description);
        when(resultSet.getDate("deadline")).thenReturn(java.sql.Date.valueOf(deadline));
        when(resultSet.getBoolean("completed")).thenReturn(completed);
        when(resultSet.getInt("project_id")).thenReturn(projectId);
        List<Task> tasks = jdbcTaskRepository.findAllByUserId(userId);
        assertEquals(2, tasks.size());
        assertEquals(taskId1, tasks.get(0).getId());
        assertEquals(taskId2, tasks.get(1).getId());
    }

    @Test
    public void testFindAllByProjectId() throws SQLException {
        int projectId = 1;
        int taskId1 = 1;
        int taskId2 = 2;
        String description = "Test Task";
        LocalDate deadline = LocalDate.of(2022, 1, 1);
        boolean completed = false;
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(anyString())).thenReturn(mock(PreparedStatement.class));
        when(connection.prepareStatement(anyString()).executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(taskId1).thenReturn(taskId2);
        when(resultSet.getString("description")).thenReturn(description);
        when(resultSet.getDate("deadline")).thenReturn(java.sql.Date.valueOf(deadline));
        when(resultSet.getBoolean("completed")).thenReturn(completed);
        List<Task> tasks = jdbcTaskRepository.findAllByProjectId(projectId);
        assertEquals(2, tasks.size());
        assertEquals(taskId1, tasks.get(0).getId());
        assertEquals(taskId2, tasks.get(1).getId());
    }

    @Test
    public void testSave() throws SQLException {
        Task task = new Task();
        task.setDescription("Test Task");
        task.setDeadline(LocalDate.of(2022, 1, 1));
        task.setCompleted(false);
        Project project = new Project();
        project.setId(1);
        task.setProject(project);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(mock(ResultSet.class));
        when(preparedStatement.getGeneratedKeys().next()).thenReturn(true);
        when(preparedStatement.getGeneratedKeys().getInt(1)).thenReturn(1);
        Task savedTask = jdbcTaskRepository.save(task);
        assertEquals(1, savedTask.getId());
    }

    @Test
    public void testUpdate() throws SQLException {
        Task task = new Task();
        task.setId(1);
        task.setDescription("Updated Test Task");
        task.setDeadline(LocalDate.of(2022, 1, 1));
        task.setCompleted(false);
        Project project = new Project();
        project.setId(1);
        task.setProject(project);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        jdbcTaskRepository.update(task);
    }

    @Test
    public void testDelete() throws SQLException {
        int taskId = 1;
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        jdbcTaskRepository.delete(taskId);
    }

    @Test
    public void testFindAll() throws SQLException {
        int taskId1 = 1;
        int taskId2 = 2;
        String description = "Test Task";
        LocalDate deadline = LocalDate.of(2022, 1, 1);
        boolean completed = false;
        int projectId = 1;
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.createStatement()).thenReturn(mock(Statement.class));
        when(connection.createStatement().executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(taskId1).thenReturn(taskId2);
        when(resultSet.getString("description")).thenReturn(description);
        when(resultSet.getDate("deadline")).thenReturn(java.sql.Date.valueOf(deadline));
        when(resultSet.getBoolean("completed")).thenReturn(completed);
        when(resultSet.getInt("project_id")).thenReturn(projectId);
        List<Task> tasks = jdbcTaskRepository.findAll();
        assertEquals(2, tasks.size());
        assertEquals(taskId1, tasks.get(0).getId());
        assertEquals(taskId2, tasks.get(1).getId());
    }
}
