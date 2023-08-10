package Proj_Board.test.service;

import Proj_Board.test.model.User;
import Proj_Board.test.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        // Given
        User user = new User();
        user.setUserId("testUser");
        user.setUserPw("testPassword");

        // When
        userService.createUser(user);

        // Then
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testFindByUser() {
        // Given
        User user = new User();
        user.setUserId("testUser");
        user.setUserPw("testPassword");

        String id = "testUser";
        String pw = "testPassword";

        when(userRepository.findByUser(id, pw)).thenReturn(Optional.of(user));

        // When
        User result = userService.findByUser(id, pw);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(id);
        assertThat(result.getUserPw()).isEqualTo(pw);
        verify(userRepository, times(1)).findByUser(id, pw);
    }

    @Test
    public void testFindByUserId() {
        // Given
        User user = new User();
        user.setUserId("testUser");
        user.setUserPw("testPassword");

        String userId = "testUser";

        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(user));

        // When
        User result = userService.findByUserId(userId);

        // Then
        verify(userRepository, times(1)).findByUserId(userId);
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);
    }
}
