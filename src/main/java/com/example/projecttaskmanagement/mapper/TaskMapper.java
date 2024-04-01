package com.example.projecttaskmanagement.mapper;

import java.util.List;

import com.example.projecttaskmanagement.dto.TaskDto;
import com.example.projecttaskmanagement.entity.Task;

public interface TaskMapper {
    TaskDto taskToDto(Task task);
    Task dtoToTask(TaskDto taskDTO);
    List<TaskDto>taskListToDTOList(List<Task> tasks);
 //   List<Task> toEntityList(List<TaskDTO> tasks);
}
