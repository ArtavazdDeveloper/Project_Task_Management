package com.example.projecttaskmanagement.service;

import java.util.List;

import com.example.projecttaskmanagement.dto.UserDTO;
import com.example.projecttaskmanagement.entity.User;
import com.example.projecttaskmanagement.mapper.UserMapper;
import com.example.projecttaskmanagement.repository.UserRepository;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TaskService taskService;
    private final ProjectService projectService;
    
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.userListToDTOList(users);
    }

    public UserDTO getUserById(int id) {
        User user = userRepository.findById(id);
        return userMapper.userToDTO(user);
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.dtoToUser(userDTO);
        userRepository.save(user);
        return userMapper.userToDTO(user);
    }

    public UserDTO updateUser(int id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id);
        if (existingUser == null) {
            return null;
        }
        User userToUpdate = userMapper.dtoToUser(userDTO);
        userToUpdate.setId(id);
        userRepository.update(userToUpdate);
        taskService.updateTasksByUserId(id, userToUpdate);
        projectService.updateProjectsByUserId(id, userToUpdate);
        return userMapper.userToDTO(userToUpdate);
    }

    public void deleteUser(int id) {
        taskService.deleteTasksByUserId(id);
        projectService.deleteProjectsByUserId(id);
        userRepository.delete(id);
    }

}
