package com.example.projecttaskmanagement.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import com.example.projecttaskmanagement.entity.User;
import com.example.projecttaskmanagement.repository.impl.JdbcUserRepository;

public class JdbcUserRepositoryTest {
    
    private Connection connection;
    private JdbcUserRepository jdbcUserRepository;

    @Before
    public void setUp() {
        connection = mock(Connection.class);
        jdbcUserRepository = new JdbcUserRepository(connection);
    }

    @Test
    public void testFindById() throws SQLException {
        int userId = 1;
        String name = "John";
        String surname = "Doe";
        String email = "john.doe@example.com";
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(anyString())).thenReturn(mock(PreparedStatement.class));
        when(connection.prepareStatement(anyString()).executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(userId);
        when(resultSet.getString("name")).thenReturn(name);
        when(resultSet.getString("surname")).thenReturn(surname);
        when(resultSet.getString("email")).thenReturn(email);
        User user = jdbcUserRepository.findById(userId);
        assertEquals(userId, user.getId());
        assertEquals(name, user.getName());
        assertEquals(surname, user.getSurname());
        assertEquals(email, user.getEmail());
    }

    @Test
    public void testFindAll() throws SQLException {
        int userId1 = 1;
        int userId2 = 2;
        String name = "John";
        String surname = "Doe";
        String email = "john.doe@example.com";
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.createStatement()).thenReturn(mock(Statement.class));
        when(connection.createStatement().executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(userId1).thenReturn(userId2);
        when(resultSet.getString("name")).thenReturn(name);
        when(resultSet.getString("surname")).thenReturn(surname);
        when(resultSet.getString("email")).thenReturn(email);
        List<User> users = jdbcUserRepository.findAll();
        assertEquals(2, users.size());
        assertEquals(userId1, users.get(0).getId());
        assertEquals(userId2, users.get(1).getId());
    }

    @Test
    public void testSave() throws SQLException {
        User user = new User();
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("john.doe@example.com");
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(mock(ResultSet.class));
        when(preparedStatement.getGeneratedKeys().next()).thenReturn(true);
        when(preparedStatement.getGeneratedKeys().getInt(1)).thenReturn(1);
        jdbcUserRepository.save(user);
        assertEquals(1, user.getId());
    }

    @Test
    public void testUpdate() throws SQLException {
        User user = new User();
        user.setId(1);
        user.setName("Updated John");
        user.setSurname("Updated Doe");
        user.setEmail("updated.john.doe@example.com");
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        jdbcUserRepository.update(user);
    }

    @Test
    public void testDelete() throws SQLException {
        int userId = 1;
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        jdbcUserRepository.delete(userId);
    }
}
