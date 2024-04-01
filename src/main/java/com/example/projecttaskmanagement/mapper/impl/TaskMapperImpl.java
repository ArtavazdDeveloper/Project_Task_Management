package com.example.projecttaskmanagement.mapper.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.example.projecttaskmanagement.dto.TaskDto;
import com.example.projecttaskmanagement.entity.Task;
import com.example.projecttaskmanagement.mapper.TaskMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskDto taskToDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setProjectId(task.getProject_id());
        dto.setUserId(task.getUser_id());
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setCompleted(task.isCompleted());
        return dto;
    }

    @Override
    public Task dtoToTask(TaskDto taskDTO) {
        Task task = new Task();
        task.setId(taskDTO.getId());
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setCompleted(taskDTO.isCompleted());
        task.setProject_id(taskDTO.getProjectId());
        task.setUser_id(taskDTO.getUserId());
        return task;
    }

    @Override
    public List<TaskDto> taskListToDTOList(List<Task> tasks) {
        return tasks.stream().map(this::taskToDto).collect(Collectors.toList());
    }
}