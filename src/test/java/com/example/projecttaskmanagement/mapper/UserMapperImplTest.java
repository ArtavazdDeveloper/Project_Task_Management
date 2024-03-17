package com.example.projecttaskmanagement.mapper;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.example.projecttaskmanagement.dto.UserDTO;
import com.example.projecttaskmanagement.entity.User;
import com.example.projecttaskmanagement.mapper.ProjectMapper;
import com.example.projecttaskmanagement.mapper.TaskMapper;
import com.example.projecttaskmanagement.mapper.impl.ProjectMapperImpl;
import com.example.projecttaskmanagement.mapper.impl.TaskMapperImpl;
import com.example.projecttaskmanagement.mapper.impl.UserMapperImpl;

public class UserMapperImplTest {

    private UserMapperImpl userMapper;

    @Before
    public void setUp() {
        ProjectMapper projectMapper = new ProjectMapperImpl();
        TaskMapper taskMapper = new TaskMapperImpl();
        userMapper = new UserMapperImpl(projectMapper, taskMapper);
    }

    @Test
    public void testUserToDTO() {
        User user = new User();
        user.setId(1);
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("john.doe@example.com");
        UserDTO userDTO = userMapper.userToDTO(user);
        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getName(), userDTO.getName());
        assertEquals(user.getSurname(), userDTO.getSurname());
        assertEquals(user.getEmail(), userDTO.getEmail());
    }

    @Test
    public void testDTOToUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setName("John");
        userDTO.setSurname("Doe");
        userDTO.setEmail("john.doe@example.com");
        User user = userMapper.dtoToUser(userDTO);
        assertEquals(userDTO.getId(), user.getId());
        assertEquals(userDTO.getName(), user.getName());
        assertEquals(userDTO.getSurname(), user.getSurname());
        assertEquals(userDTO.getEmail(), user.getEmail());
    }

    @Test
    public void testUserListToDTOList() {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setId(1);
        user1.setName("John");
        user1.setSurname("Doe");
        user1.setEmail("john.doe@example.com");
        users.add(user1);
        User user2 = new User();
        user2.setId(2);
        user2.setName("Jane");
        user2.setSurname("Doe");
        user2.setEmail("jane.doe@example.com");
        users.add(user2);
        List<UserDTO> userDTOs = userMapper.userListToDTOList(users);
        assertEquals(users.size(), userDTOs.size());
        for (int i = 0; i < users.size(); i++) {
            assertEquals(users.get(i).getId(), userDTOs.get(i).getId());
            assertEquals(users.get(i).getName(), userDTOs.get(i).getName());
            assertEquals(users.get(i).getSurname(), userDTOs.get(i).getSurname());
            assertEquals(users.get(i).getEmail(), userDTOs.get(i).getEmail());
        }
    }
}

