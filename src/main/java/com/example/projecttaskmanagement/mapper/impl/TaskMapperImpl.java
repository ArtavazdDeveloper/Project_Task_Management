package com.example.projecttaskmanagement.mapper.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.example.projecttaskmanagement.dto.TaskDTO;
import com.example.projecttaskmanagement.entity.Task;
import com.example.projecttaskmanagement.mapper.TaskMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskDTO taskToDto(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setDeadline(task.getDeadline());
        dto.setCompleted(task.isCompleted());
        dto.setProject(task.getProject());
        return dto;
    }

    @Override
    public Task dtoToTask(TaskDTO taskDTO) {
        Task task = new Task();
        task.setId(taskDTO.getId());
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setDeadline(taskDTO.getDeadline());
        task.setCompleted(taskDTO.isCompleted());
        return task;
    }

    @Override
    public List<TaskDTO> taskListToDTOList(List<Task> tasks) {
        return tasks.stream().map(this::taskToDto).collect(Collectors.toList());
    }
}