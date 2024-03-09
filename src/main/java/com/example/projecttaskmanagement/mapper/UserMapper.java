package com.example.projecttaskmanagement.mapper;

import com.example.projecttaskmanagement.dto.UserDTO;
import com.example.projecttaskmanagement.entity.User;

import java.util.List;



public interface UserMapper {
    UserDTO userToDTO(User user);
    User dtoToUser(UserDTO userDTO);
    List<UserDTO> userListToDTOList(List<User>users);
}
