package com.example.projecttaskmanagement.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import com.example.projecttaskmanagement.db.DBConnectionProvider;
import com.example.projecttaskmanagement.entity.Project;
import com.example.projecttaskmanagement.entity.Task;
import com.example.projecttaskmanagement.repository.ProjectRepository;

import javax.sql.DataSource;

public class JdbcProjectRepository implements ProjectRepository{

    private final DataSource dataSource;

    public JdbcProjectRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Project findById(int id) {
        String query = "SELECT * FROM projects WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractProjectFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Project> findAll() {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM projects";
        try (Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                projects.add(extractProjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    @Override
    public void save(Project project) {
        String query = "INSERT INTO projects (name, description) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                project.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Project project) {
        String query = "UPDATE projects SET name = ?, description = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());

            statement.setInt(3, project.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM projects WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Project extractProjectFromResultSet(ResultSet resultSet) throws SQLException {
        Project project = new Project();
        project.setId(resultSet.getInt("id"));
        project.setName(resultSet.getString("name"));
        project.setDescription(resultSet.getString("description"));
        return project;
    }

    @Override
    public void addTaskToProject(int projectId, Task task) {
        String query = "INSERT INTO task (name, description, completed, project_id, user_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, task.getName());
            statement.setString(2, task.getDescription());
            statement.setBoolean(3, task.isCompleted());
            statement.setInt(4, projectId);
            statement.setInt(5, task.getUser_id());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                task.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Project> findAllByUserId(int userId) {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM projects WHERE user_id = ?";
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Project project = new Project();
                    project.setId(resultSet.getInt("id"));
                    project.setName(resultSet.getString("name"));
                    project.setDescription(resultSet.getString("description"));
                    projects.add(project);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }
}
