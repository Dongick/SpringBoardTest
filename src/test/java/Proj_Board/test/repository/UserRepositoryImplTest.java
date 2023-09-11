package Proj_Board.test.repository;

import Proj_Board.test.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.*;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserRepositoryImplTest {

    @InjectMocks
    private UserRepositoryImpl userRepository;

    @Mock
    private EntityManager em;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void save(){
        User user = new User();
        user.setUserId("2018202004");
        user.setUserPw("1357kjw1");

        userRepository.save(user);

        verify(em, times(1)).persist(user);
    }
    @Test
    public void findByUserId(){
        User user = new User();
        user.setId(1L);
        user.setUserId("2018202004");
        user.setUserPw("1357kjw1");

        String userId = "2018202004";

        TypedQuery<User> mockTypedQuery = mock(TypedQuery.class);
        when(mockTypedQuery.setParameter(eq("userId"), eq(userId))).thenReturn(mockTypedQuery);
        when(mockTypedQuery.getSingleResult()).thenReturn(user);

        when(em.createQuery("SELECT u From User u WHERE u.userId = :userId", User.class)).thenReturn(mockTypedQuery);

        // When
        Optional<User> result = userRepository.findByUserId(userId);

        // Then
        verify(em, times(1)).createQuery("SELECT u From User u WHERE u.userId = :userId", User.class);
        verify(mockTypedQuery, times(1)).setParameter("userId", userId);
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(user);
    }

    @Test
    public void findByUser(){
        User user = new User();
        user.setId(1L);
        user.setUserId("2018202004");
        user.setUserPw("1357kjw1");

        String id = "2018202004";
        String pw = "1357kjw1";

        TypedQuery<User> mockTypedQuery = mock(TypedQuery.class);
        when(mockTypedQuery.setParameter(eq("id"), eq(id))).thenReturn(mockTypedQuery);
        when(mockTypedQuery.setParameter(eq("pw"), eq(pw))).thenReturn(mockTypedQuery);
        when(mockTypedQuery.getSingleResult()).thenReturn(user);

        when(em.createQuery("SELECT u From User u WHERE u.userId = :id AND u.userPw = :pw", User.class)).thenReturn(mockTypedQuery);

        // When
        Optional<User> result = userRepository.findByUser(id, pw);

        // Then
        verify(em, times(1)).createQuery("SELECT u From User u WHERE u.userId = :id AND u.userPw = :pw", User.class);
        verify(mockTypedQuery, times(1)).setParameter("id", id);
        verify(mockTypedQuery, times(1)).setParameter("pw", pw);
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(user);
    }
}
