package com.example.projecttaskmanagement.service;

import java.util.List;

import com.example.projecttaskmanagement.db.DBConnectionProvider;
import com.example.projecttaskmanagement.dto.UserDto;
import com.example.projecttaskmanagement.entity.User;
import com.example.projecttaskmanagement.mapper.ProjectMapper;
import com.example.projecttaskmanagement.mapper.UserMapper;
import com.example.projecttaskmanagement.mapper.impl.ProjectMapperImpl;
import com.example.projecttaskmanagement.mapper.impl.UserMapperImpl;
import com.example.projecttaskmanagement.repository.ProjectRepository;
import com.example.projecttaskmanagement.repository.UserRepository;
import com.example.projecttaskmanagement.repository.impl.JdbcProjectRepository;
import com.example.projecttaskmanagement.repository.impl.JdbcUserRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;


public class UserService {


    private final UserRepository userRepository = new JdbcUserRepository(DBConnectionProvider.getDataSource());
    private final UserMapper userMapper = new UserMapperImpl();
    private final TaskService taskService;
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final ProjectService projectService;

    public UserService(TaskService taskService, ProjectRepository projectRepository, ProjectMapper projectMapper, ProjectService projectService) {
        this.taskService = taskService;
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.projectService = projectService;
    }



    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.userListToDTOList(users);
    }

    public UserDto getUserById(int id) {
        User user = userRepository.findById(id);
        return userMapper.userToDTO(user);
    }

    public UserDto createUser(UserDto userDTO) {
        User user = userMapper.dtoToUser(userDTO);
        userRepository.save(user);
        return userMapper.userToDTO(user);
    }

    public UserDto updateUser(int id, UserDto userDTO) {
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
        //taskService.updateTasksByUserId(id, existingUser);
        //projectService.updateProjectsByUserId(id, existingUser);
        return userMapper.userToDTO(existingUser);
    }

    public void deleteUser(int id) {
        //taskService.deleteTasksByUserId(id);
        //projectService.deleteProjectsByUserId(id);
        userRepository.delete(id);
    }

}
