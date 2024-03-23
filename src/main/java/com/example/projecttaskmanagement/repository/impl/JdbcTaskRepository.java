package com.example.projecttaskmanagement.repository.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.projecttaskmanagement.db.DBConnectionProvider;
import com.example.projecttaskmanagement.entity.Project;
import com.example.projecttaskmanagement.entity.Task;
import com.example.projecttaskmanagement.repository.TaskRepository;

public class JdbcTaskRepository implements TaskRepository{

    private final Connection connection;

    public JdbcTaskRepository(Connection connection) {
        this.connection = DBConnectionProvider.connectionDB();
    }

    @Override
    public Task findById(int id) {
        String query = "SELECT * FROM task WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractTaskFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<Task> findAllByUserId(int userId) {
    List<Task> tasks = new ArrayList<>();
    String query = "SELECT * FROM task WHERE user_id = ?";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            tasks.add(extractTaskFromResultSet(resultSet));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return tasks;
}

    @Override
    public List<Task> findAllByProjectId(int projectId) {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM task WHERE project_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, projectId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tasks.add(extractTaskFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public Task save(Task task) {
        String query = "INSERT INTO task (name,description, completed, project_id) VALUES (?, ?, ?, ?)";
    try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
        statement.setString(1, task.getName());
        statement.setString(2, task.getDescription());
        statement.setBoolean(3, task.isCompleted());
        statement.setInt(4, task.getProject().getId());
        statement.executeUpdate();
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            task.setId(generatedKeys.getInt(1));
        }
        return task;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
    }

    @Override
    public void update(Task task) {
        String query = "UPDATE task SET description = ?, deadline = ?, completed = ?, project_id = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, task.getDescription());
            statement.setBoolean(3, task.isCompleted());
            statement.setInt(4, task.getProject().getId());
            statement.setInt(5, task.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM task WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Task extractTaskFromResultSet(ResultSet resultSet) throws SQLException {
        Task task = new Task();
        task.setId(resultSet.getInt("id"));
        task.setDescription(resultSet.getString("description"));
        task.setCompleted(resultSet.getBoolean("completed"));
        int projectId = resultSet.getInt("project_id");
        Project project = new Project();
        project.setId(projectId);
        task.setProject(project);
        return task;
    }

    @Override
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
    String query = "SELECT * FROM task";
    try (Statement statement = connection.createStatement()) {
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            tasks.add(extractTaskFromResultSet(resultSet));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return tasks;
}

}