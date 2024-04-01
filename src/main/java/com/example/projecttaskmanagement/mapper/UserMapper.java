package com.example.projecttaskmanagement.mapper;

import com.example.projecttaskmanagement.dto.UserDto;
import com.example.projecttaskmanagement.entity.User;

import java.util.List;

public interface UserMapper {
    UserDto userToDTO(User user);
    User dtoToUser(UserDto userDTO);
    List<UserDto> userListToDTOList(List<User>users);
}
