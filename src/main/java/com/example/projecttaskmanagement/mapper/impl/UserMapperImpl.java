package com.example.projecttaskmanagement.mapper.impl;

import com.example.projecttaskmanagement.dto.UserDto;
import com.example.projecttaskmanagement.entity.User;
import com.example.projecttaskmanagement.mapper.ProjectMapper;
import com.example.projecttaskmanagement.mapper.TaskMapper;
import com.example.projecttaskmanagement.mapper.UserMapper;



import java.util.List;
import java.util.stream.Collectors;


public class UserMapperImpl implements UserMapper {

    private final ProjectMapper projectMapper;
    private final TaskMapper taskMapper;


    public UserMapperImpl() {
        this.projectMapper = new ProjectMapperImpl();
        this.taskMapper = new TaskMapperImpl();
    }


    public UserMapperImpl(ProjectMapper projectMapper, TaskMapper taskMapper) {
        this.projectMapper = projectMapper;
        this.taskMapper = taskMapper;
    }


    @Override
    public UserDto userToDTO(User user) {
        UserDto userDTO = new UserDto();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    @Override
    public User dtoToUser(UserDto userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        return user;
    }

    @Override
    public List<UserDto> userListToDTOList(List<User> users) {
        return users.stream().map(this::userToDTO).collect(Collectors.toList());
    }
}
