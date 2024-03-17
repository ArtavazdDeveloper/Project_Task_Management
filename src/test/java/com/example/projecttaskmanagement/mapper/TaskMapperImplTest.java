package com.example.projecttaskmanagement.mapper;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.example.projecttaskmanagement.dto.TaskDTO;
import com.example.projecttaskmanagement.entity.Task;
import com.example.projecttaskmanagement.mapper.TaskMapper;
import com.example.projecttaskmanagement.mapper.impl.TaskMapperImpl;

public class TaskMapperImplTest {

    private TaskMapper taskMapper;

    @Before
    public void setUp() {
        taskMapper = new TaskMapperImpl();
    }

    @Test
    public void testTaskToDto() {
        Task task = new Task();
        task.setId(1);
        task.setName("Task 1");
        task.setDescription("Description for Task 1");
        task.setDeadline(LocalDate.of(2024, 3, 31));
        task.setCompleted(false);
        TaskDTO taskDTO = taskMapper.taskToDto(task);
        assertEquals(task.getId(), taskDTO.getId());
        assertEquals(task.getName(), taskDTO.getName());
        assertEquals(task.getDescription(), taskDTO.getDescription());
        assertEquals(task.getDeadline(), taskDTO.getDeadline());
        assertEquals(task.isCompleted(), taskDTO.isCompleted());
    }

    @Test
    public void testDtoToTask() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(1);
        taskDTO.setName("Task 1");
        taskDTO.setDescription("Description for Task 1");
        taskDTO.setDeadline(LocalDate.of(2024, 3, 31));
        taskDTO.setCompleted(false);
        Task task = taskMapper.dtoToTask(taskDTO);
        assertEquals(taskDTO.getId(), task.getId());
        assertEquals(taskDTO.getName(), task.getName());
        assertEquals(taskDTO.getDescription(), task.getDescription());
        assertEquals(taskDTO.getDeadline(), task.getDeadline());
        assertEquals(taskDTO.isCompleted(), task.isCompleted());
    }

    @Test
    public void testTaskListToDTOList() {
        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task();
        task1.setId(1);
        task1.setName("Task 1");
        task1.setDescription("Description for Task 1");
        task1.setDeadline(LocalDate.of(2024, 3, 31));
        task1.setCompleted(false);
        tasks.add(task1);
        Task task2 = new Task();
        task2.setId(2);
        task2.setName("Task 2");
        task2.setDescription("Description for Task 2");
        task2.setDeadline(LocalDate.of(2024, 4, 1));
        task2.setCompleted(true);
        tasks.add(task2);
        List<TaskDTO> taskDTOs = taskMapper.taskListToDTOList(tasks);
        assertEquals(tasks.size(), taskDTOs.size());
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            TaskDTO taskDTO = taskDTOs.get(i);
            assertEquals(task.getId(), taskDTO.getId());
            assertEquals(task.getName(), taskDTO.getName());
            assertEquals(task.getDescription(), taskDTO.getDescription());
            assertEquals(task.getDeadline(), taskDTO.getDeadline());
            assertEquals(task.isCompleted(), taskDTO.isCompleted());
        }
    }
}

