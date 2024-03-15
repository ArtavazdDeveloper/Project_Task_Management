package com.example.projecttaskmanagement.mapper;

import java.util.List;

import com.example.projecttaskmanagement.dto.TaskDTO;
import com.example.projecttaskmanagement.entity.Task;

public interface TaskMapper {
    TaskDTO taskToDto(Task task);
    Task dtoToTask(TaskDTO taskDTO);
    List<TaskDTO>taskListToDTOList(List<Task> tasks);
 //   List<Task> toEntityList(List<TaskDTO> tasks);
}
