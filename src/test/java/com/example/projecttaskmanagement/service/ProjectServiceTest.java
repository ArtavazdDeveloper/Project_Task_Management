//package com.example.projecttaskmanagement.service;
//
//import com.example.projecttaskmanagement.dto.ProjectDto;
//import com.example.projecttaskmanagement.dto.TaskDto;
//import com.example.projecttaskmanagement.dto.UserDto;
//import com.example.projecttaskmanagement.entity.Project;
//import com.example.projecttaskmanagement.entity.Task;
//import com.example.projecttaskmanagement.entity.User;
//import com.example.projecttaskmanagement.mapper.ProjectMapper;
//import com.example.projecttaskmanagement.mapper.UserMapper;
//import com.example.projecttaskmanagement.repository.ProjectRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class ProjectServiceTest {
//
//    @Mock
//    private ProjectRepository projectRepository;
//
//    @Mock
//    private ProjectMapper projectMapper;
//
//    @Mock
//    private UserMapper userMapper;
//
//    private ProjectService projectService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        projectService = new ProjectService(projectRepository, projectMapper, userMapper);
//    }
//
//    @Nested
//    @DisplayName("Tests for getAllProjects method")
//    class GetAllProjectsTests {
//        @Test
//        void testGetAllProjects() {
//            // Arrange
//            List<Project> projects = Collections.singletonList(new Project());
//            when(projectRepository.findAll()).thenReturn(projects);
//
//            List<ProjectDto> expectedProjectDtos = Collections.singletonList(new ProjectDto());
//            when(projectMapper.projectListToDTOList(projects)).thenReturn(expectedProjectDtos);
//
//            // Act
//            List<ProjectDto> projectDtos = projectService.getAllProjects();
//
//            // Assert
//            assertEquals(expectedProjectDtos, projectDtos);
//        }
//    }
//
//    @Nested
//    @DisplayName("Tests for getProjectById method")
//    class GetProjectByIdTests {
//        @Test
//        void testGetProjectById() {
//            // Arrange
//            Project project = new Project();
//            ProjectDto expectedProjectDto = new ProjectDto();
//            when(projectRepository.findById(1)).thenReturn(project);
//            when(projectMapper.projectToDTO(project)).thenReturn(expectedProjectDto);
//
//            // Act
//            ProjectDto projectDto = projectService.getProjectById(1);
//
//            // Assert
//            assertEquals(expectedProjectDto, projectDto);
//        }
//
//        @Test
//        void testGetProjectById_NonexistentId() {
//            // Arrange
//            when(projectRepository.findById(100)).thenReturn(null);
//
//            // Act
//            ProjectDto projectDto = projectService.getProjectById(100);
//
//            // Assert
//            assertNull(projectDto);
//        }
//    }
//
//    @Nested
//    @DisplayName("Tests for createProject method")
//    class CreateProjectTests {
//        @Test
//        void testCreateProject() {
//            // Arrange
//            ProjectDto projectDto = new ProjectDto();
//            Project project = new Project();
//            when(projectMapper.dtoToProject(projectDto)).thenReturn(project);
//            doNothing().when(projectRepository).save(project);
//
//            // Act
//            ProjectDto createdProjectDto = projectService.createProject(projectDto);
//
//            // Assert
//            assertEquals(projectDto, createdProjectDto);
//        }
//    }
//
//
//
//    @Nested
//    @DisplayName("Tests for updateProject method")
//    class UpdateProjectTests {
//        @Test
//        void testUpdateProject() {
//            // Arrange
//            int id = 1;
//            ProjectDto projectDto = new ProjectDto();
//            Project projectToUpdate = new Project();
//            Project updatedProject = new Project();
//            when(projectRepository.findById(id)).thenReturn(projectToUpdate);
//            when(projectMapper.dtoToProject(projectDto)).thenReturn(updatedProject);
//            when(projectRepository.update(updatedProject)).thenReturn(updatedProject);
//            when(projectMapper.projectToDTO(updatedProject)).thenReturn(projectDto);
//
//            // Act
//            ProjectDto updatedProjectDto = projectService.updateProject(id, projectDto);
//
//            // Assert
//            assertEquals(projectDto, updatedProjectDto);
//        }
//
//        @Test
//        void testUpdateProject_NonexistentId() {
//            // Arrange
//            int id = 100;
//            ProjectDto projectDto = new ProjectDto();
//            when(projectRepository.findById(id)).thenReturn(null);
//
//            // Act
//            ProjectDto updatedProjectDto = projectService.updateProject(id, projectDto);
//
//            // Assert
//            assertNull(updatedProjectDto);
//        }
//    }
//
//    @Nested
//    @DisplayName("Tests for deleteProject method")
//    class DeleteProjectTests {
//        @Test
//        void testDeleteProject() {
//            // Arrange
//            int id = 1;
//
//            // Act
//            projectService.deleteProject(id);
//
//            // Assert
//            verify(projectRepository, times(1)).delete(id);
//        }
//    }
//
//    @Nested
//    @DisplayName("Tests for updateProjectsByUserId method")
//    class UpdateProjectsByUserIdTests {
//        @Test
//        void testUpdateProjectsByUserId() {
//            // Arrange
//            int userId = 1;
//            User updatedUser = new User();
//            List<Project> projects = new ArrayList<>();
//            projects.add(new Project());
//            when(projectRepository.findAllByUserId(userId)).thenReturn(projects);
//
//            // Act
//            projectService.updateProjectsByUserId(userId, updatedUser);
//
//            // Assert
//            verify(projectRepository, times(1)).update(any());
//        }
//    }
//
//    @Nested
//    @DisplayName("Tests for addTaskToProject method")
//    class AddTaskToProjectTests {
//        @Test
//        void testAddTaskToProject() {
//            // Arrange
//            int projectId = 1;
//            TaskDto taskDto = new TaskDto();
//            Task task = new Task();
//            when(projectRepository.addTaskToProject(projectId, task)).thenReturn(true);
//
//            // Act
//            projectService.addTaskToProject(projectId, taskDto);
//
//            // Assert
//            verify(projectRepository, times(1)).addTaskToProject(projectId, task);
//        }
//    }
//
//    @Nested
//    @DisplayName("Tests for addUserToProject method")
//    class AddUserToProjectTests {
//        @Test
//        void testAddUserToProject() {
//            // Arrange
//            int projectId = 1;
//            int userId = 1;
//            UserDto userDto = new UserDto();
//            Project project = new Project();
//            when(projectRepository.findById(projectId)).thenReturn(project);
//
//            // Act
//            projectService.addUserToProject(projectId, userId, userDto);
//
//            // Assert
//            verify(projectRepository, times(1)).update(project);
//        }
//    }
//
//    @Nested
//    @DisplayName("Tests for deleteProjectsByUserId method")
//    class DeleteProjectsByUserIdTests {
//        @Test
//        void testDeleteProjectsByUserId() {
//            // Arrange
//            int userId = 1;
//            List<Project> projects = new ArrayList<>();
//            projects.add(new Project());
//            when(projectRepository.findAllByUserId(userId)).thenReturn(projects);
//
//            // Act
//            projectService.deleteProjectsByUserId(userId);
//
//            // Assert
//            verify(projectRepository, times(projects.size())).delete(anyInt());
//        }
//    }
//}
