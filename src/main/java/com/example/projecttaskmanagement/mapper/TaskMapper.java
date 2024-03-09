package com.example.projecttaskmanagement.mapper;

import com.example.projecttaskmanagement.dto.TaskDTO;
import com.example.projecttaskmanagement.entity.Task;

import java.util.List;



public interface TaskMapper {
    TaskDTO taskToDto(Task task);
    Task dtoToTask(TaskDTO taskDTO);
    List<TaskDTO>taskListToDTOList(List<Task> tasks);
}
