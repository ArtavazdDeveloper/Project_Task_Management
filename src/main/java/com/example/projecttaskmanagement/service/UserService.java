package com.example.projecttaskmanagement.service;

import java.util.List;

import com.example.projecttaskmanagement.db.DBConnectionProvider;
import com.example.projecttaskmanagement.dto.UserDTO;
import com.example.projecttaskmanagement.entity.User;
import com.example.projecttaskmanagement.mapper.ProjectMapper;
import com.example.projecttaskmanagement.mapper.UserMapper;
import com.example.projecttaskmanagement.mapper.impl.UserMapperImpl;
import com.example.projecttaskmanagement.repository.ProjectRepository;
import com.example.projecttaskmanagement.repository.UserRepository;
import com.example.projecttaskmanagement.repository.impl.JdbcUserRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class UserService {


    private UserRepository userRepository = new JdbcUserRepository(DBConnectionProvider.connectionDB());
    private final UserMapper userMapper = new UserMapperImpl();
    private final TaskService taskService = new TaskService();
    private final ProjectService projectService = new ProjectService();


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
        if (userDTO.getName() != null) {
            existingUser.setName(userDTO.getName());
        }
        if (userDTO.getSurname() != null) {
            existingUser.setSurname(userDTO.getSurname());
        }
        if (userDTO.getEmail() != null) {
            existingUser.setEmail(userDTO.getEmail());
        }
        userRepository.update(existingUser);
        taskService.updateTasksByUserId(id, existingUser);
        projectService.updateProjectsByUserId(id, existingUser);
        return userMapper.userToDTO(existingUser);
    }

    public void deleteUser(int id) {
        taskService.deleteTasksByUserId(id);
        projectService.deleteProjectsByUserId(id);
        userRepository.delete(id);
    }

}
