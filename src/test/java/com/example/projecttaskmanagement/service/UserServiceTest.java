package com.example.projecttaskmanagement.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.example.projecttaskmanagement.dto.UserDTO;
import com.example.projecttaskmanagement.entity.User;
import com.example.projecttaskmanagement.mapper.UserMapper;
import com.example.projecttaskmanagement.repository.UserRepository;

public class UserServiceTest {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private TaskService taskService;
    private ProjectService projectService;
    private UserService userService;

    @Before
    public void setUp() {
        userRepository = mock(UserRepository.class);
        userMapper = mock(UserMapper.class);
        taskService = mock(TaskService.class);
        projectService = mock(ProjectService.class);
        userService = new UserService(userRepository, userMapper, taskService, projectService);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setId(1);
        users.add(user);
        List<UserDTO> userDTOs = new ArrayList<>();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTOs.add(userDTO);
        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.userListToDTOList(users)).thenReturn(userDTOs);
        List<UserDTO> result = userService.getAllUsers();
        assertEquals(userDTOs, result);
    }

    @Test
    public void testGetUserById() {
        int userId = 1;
        User user = new User();
        user.setId(userId);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        when(userRepository.findById(userId)).thenReturn(user);
        when(userMapper.userToDTO(user)).thenReturn(userDTO);
        UserDTO result = userService.getUserById(userId);
        assertEquals(userDTO, result);
    }

    @Test
    public void testGetUserById_NotFound() {
        int userId = 1;
        when(userRepository.findById(userId)).thenReturn(null);
        UserDTO result = userService.getUserById(userId);
        assertNull(result);
    }

    @Test
    public void testCreateUser() {
        UserDTO userDTO = new UserDTO();
        User user = new User();
        when(userMapper.dtoToUser(userDTO)).thenReturn(user);
        UserDTO createdUserDTO = userService.createUser(userDTO);
        assertEquals(userDTO, createdUserDTO);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateUser() {
        int userId = 1;
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        User existingUser = new User();
        when(userRepository.findById(userId)).thenReturn(existingUser);
        User updatedUser = new User();
        when(userMapper.dtoToUser(userDTO)).thenReturn(updatedUser);
        UserDTO updatedUserDTO = userService.updateUser(userId, userDTO);
        assertEquals(userDTO, updatedUserDTO);
        verify(userRepository, times(1)).update(updatedUser);
        verify(taskService, times(1)).updateTasksByUserId(userId, updatedUser);
        verify(projectService, times(1)).updateProjectsByUserId(userId, updatedUser);
    }

    @Test
    public void testUpdateUser_NotFound() {
        int userId = 1;
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        when(userRepository.findById(userId)).thenReturn(null);
        UserDTO updatedUserDTO = userService.updateUser(userId, userDTO);
        assertNull(updatedUserDTO);
        verify(userRepository, never()).update(any());
        verify(taskService, never()).updateTasksByUserId(anyInt(), any());
        verify(projectService, never()).updateProjectsByUserId(anyInt(), any());
    }

    @Test
    public void testDeleteUser() {
        int userId = 1;
        userService.deleteUser(userId);
        verify(taskService, times(1)).deleteTasksByUserId(userId);
        verify(projectService, times(1)).deleteProjectsByUserId(userId);
        verify(userRepository, times(1)).delete(userId);
    }
}
