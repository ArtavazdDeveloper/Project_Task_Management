package com.example.projecttaskmanagement.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.projecttaskmanagement.db.DBConnectionProvider;
import com.example.projecttaskmanagement.entity.Project;
import com.example.projecttaskmanagement.repository.impl.JdbcProjectRepository;

public class JdbcProjectRepositoryTest {
    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @Mock
    private DBConnectionProvider dbConnectionProvider;

    private JdbcProjectRepository jdbcProjectRepository;

    @Before
    public void setup() throws SQLException {
        MockitoAnnotations.initMocks(this);
        when(dbConnectionProvider.getConnection()).thenReturn(connection);
        jdbcProjectRepository = new JdbcProjectRepository(dbConnectionProvider);
    }

    @Test
    public void testFindById() throws SQLException {
        int projectId = 1;
        String projectName = "Test Project";
        String projectDescription = "Test Description";
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 12, 31);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(projectId);
        when(resultSet.getString("name")).thenReturn(projectName);
        when(resultSet.getString("description")).thenReturn(projectDescription);
        when(resultSet.getDate("start_date")).thenReturn(Date.valueOf(startDate));
        when(resultSet.getDate("end_date")).thenReturn(Date.valueOf(endDate));
        Project project = jdbcProjectRepository.findById(projectId);
        assertEquals(projectId, project.getId());
        assertEquals(projectName, project.getName());
        assertEquals(projectDescription, project.getDescription());
        assertEquals(startDate, project.getStartDate());
        assertEquals(endDate, project.getEndDate());
    }

    @Test
    public void testFindAll() throws SQLException {
        int projectId1 = 1;
        int projectId2 = 2;
        String projectName1 = "Test Project 1";
        String projectName2 = "Test Project 2";
        String projectDescription = "Test Description";
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 12, 31);
        when(connection.createStatement()).thenReturn(mock(Statement.class));
        when(connection.createStatement().executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(projectId1).thenReturn(projectId2);
        when(resultSet.getString("name")).thenReturn(projectName1).thenReturn(projectName2);
        when(resultSet.getString("description")).thenReturn(projectDescription);
        when(resultSet.getDate("start_date")).thenReturn(Date.valueOf(startDate));
        when(resultSet.getDate("end_date")).thenReturn(Date.valueOf(endDate));
        List<Project> projects = jdbcProjectRepository.findAll();
        assertEquals(2, projects.size());
        assertEquals(projectId1, projects.get(0).getId());
        assertEquals(projectName1, projects.get(0).getName());
        assertEquals(projectId2, projects.get(1).getId());
        assertEquals(projectName2, projects.get(1).getName());
    }

    @Test
    public void testSave() throws SQLException {
        Project project = new Project();
        project.setName("Test Project");
        project.setDescription("Test Description");
        project.setStartDate(LocalDate.of(2022, 1, 1));
        project.setEndDate(LocalDate.of(2022, 12, 31));
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);
        jdbcProjectRepository.save(project);
        assertEquals(1, project.getId());
    }

    @Test
    public void testUpdate() throws SQLException {
        Project project = new Project();
        project.setId(1);
        project.setName("Updated Test Project");
        project.setDescription("Updated Test Description");
        project.setStartDate(LocalDate.of(2022, 1, 1));
        project.setEndDate(LocalDate.of(2022, 12, 31));
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        jdbcProjectRepository.update(project);
    }

    @Test
    public void testDelete() throws SQLException {
        int projectId = 1;
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        jdbcProjectRepository.delete(projectId);
    }

    @Test
    public void testFindAllByUserId() throws SQLException {
        int userId = 1;
        int projectId1 = 1;
        int projectId2 = 2;
        String projectName1 = "Test Project 1";
        String projectName2 = "Test Project 2";
        String projectDescription = "Test Description";
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(projectId1).thenReturn(projectId2);
        when(resultSet.getString("name")).thenReturn(projectName1).thenReturn(projectName2);
        when(resultSet.getString("description")).thenReturn(projectDescription);
        List<Project> projects = jdbcProjectRepository.findAllByUserId(userId);
        assertEquals(2, projects.size());
        assertEquals(projectId1, projects.get(0).getId());
        assertEquals(projectName1, projects.get(0).getName());
        assertEquals(projectId2, projects.get(1).getId());
        assertEquals(projectName2, projects.get(1).getName());
    }
}
