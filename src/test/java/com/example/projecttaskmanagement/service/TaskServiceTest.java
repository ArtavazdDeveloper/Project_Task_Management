package com.example.projecttaskmanagement.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.example.projecttaskmanagement.dto.TaskDTO;
import com.example.projecttaskmanagement.entity.Task;
import com.example.projecttaskmanagement.entity.User;
import com.example.projecttaskmanagement.mapper.TaskMapper;
import com.example.projecttaskmanagement.repository.TaskRepository;

public class TaskServiceTest {

    private TaskRepository taskRepository;
    private TaskMapper taskMapper;
    private TaskService taskService;

    @Before
    public void setUp() {
        taskRepository = mock(TaskRepository.class);
        taskMapper = mock(TaskMapper.class);
        taskService = new TaskService(taskRepository, taskMapper);
    }

    @Test
    public void testGetAllTasks() {
        List<Task> tasks = new ArrayList<>();
        Task task = new Task();
        task.setId(1);
        tasks.add(task);
        List<TaskDTO> taskDTOs = new ArrayList<>();
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(1);
        taskDTOs.add(taskDTO);
        when(taskRepository.findAll()).thenReturn(tasks);
        when(taskMapper.taskListToDTOList(tasks)).thenReturn(taskDTOs);
        List<TaskDTO> result = taskService.getAllTasks();
        assertEquals(taskDTOs, result);
    }

    @Test
    public void testGetTaskById() {
        int taskId = 1;
        Task task = new Task();
        task.setId(taskId);
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(taskId);
        when(taskRepository.findById(taskId)).thenReturn(task);
        when(taskMapper.taskToDto(task)).thenReturn(taskDTO);
        TaskDTO result = taskService.getTaskById(taskId);
        assertEquals(taskDTO, result);
    }

    @Test
    public void testGetTaskById_NotFound() {
        int taskId = 1;
        when(taskRepository.findById(taskId)).thenReturn(null);
        TaskDTO result = taskService.getTaskById(taskId);
        assertNull(result);
    }

    @Test
    public void testCreateTask() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setName("Task 1");
        Task task = new Task();
        task.setId(1);
        task.setName("Task 1");
        when(taskMapper.dtoToTask(taskDTO)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.taskToDto(task)).thenReturn(taskDTO);
        TaskDTO result = taskService.createTask(taskDTO);
        assertEquals(taskDTO, result);
    }

    @Test
    public void testUpdateTask() {
        int taskId = 1;
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setName("Updated Task 1");
        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setName("Task 1");
        Task taskToUpdate = new Task();
        taskToUpdate.setId(taskId);
        taskToUpdate.setName("Updated Task 1");
        when(taskRepository.findById(taskId)).thenReturn(existingTask);
        when(taskMapper.dtoToTask(taskDTO)).thenReturn(taskToUpdate);
        TaskDTO result = taskService.updateTask(taskId, taskDTO);
        assertEquals(taskDTO, result);
        verify(taskRepository).update(taskToUpdate);
    }

    @Test
    public void testDeleteTask() {
        int taskId = 1;
        taskService.deleteTask(taskId);
        verify(taskRepository).delete(taskId);
    }

    @Test
    public void testGetTasksByProjectId() {
        int projectId = 1;
        List<Task> tasks = new ArrayList<>();
        Task task = new Task();
        task.setId(1);
        tasks.add(task);
        List<TaskDTO> taskDTOs = new ArrayList<>();
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(1);
        taskDTOs.add(taskDTO);
        when(taskRepository.findAllByProjectId(projectId)).thenReturn(tasks);
        when(taskMapper.taskListToDTOList(tasks)).thenReturn(taskDTOs);
        List<TaskDTO> result = taskService.getTasksByProjectId(projectId);
        assertEquals(taskDTOs, result);
    }

    @Test
    public void testGetTasksByUserId() {
        int userId = 1;
        List<Task> tasks = new ArrayList<>();
        Task task = new Task();
        task.setId(1);
        tasks.add(task);
        List<TaskDTO> taskDTOs = new ArrayList<>();
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(1);
        taskDTOs.add(taskDTO);
        when(taskRepository.findAllByUserId(userId)).thenReturn(tasks);
        when(taskMapper.taskListToDTOList(tasks)).thenReturn(taskDTOs);
        List<TaskDTO> result = taskService.getTasksByUserId(userId);
        assertEquals(taskDTOs, result);
    }

    @Test
    public void testUpdateTasksByUserId() {
        int userId = 1;
        User updatedUser = new User();
        updatedUser.setId(userId);
        List<Task> tasks = new ArrayList<>();
        Task task = new Task();
        task.setId(1);
        tasks.add(task);
        when(taskRepository.findAllByUserId(userId)).thenReturn(tasks);
        taskService.updateTasksByUserId(userId, updatedUser);
        for (Task t : tasks) {
            assertEquals(updatedUser, t.getUser());
            verify(taskRepository).update(t);
        }
    }

    @Test
    public void testDeleteTasksByUserId() {
        int userId = 1;
        List<Task> tasks = new ArrayList<>();
        Task task = new Task();
        task.setId(1);
        tasks.add(task);
        when(taskRepository.findAllByUserId(userId)).thenReturn(tasks);
        taskService.deleteTasksByUserId(userId);
        for (Task t : tasks) {
            verify(taskRepository).delete(t.getId());
        }
    }
}
