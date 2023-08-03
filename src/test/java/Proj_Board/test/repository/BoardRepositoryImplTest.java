package Proj_Board.test.repository;

import Proj_Board.test.model.Board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BoardRepositoryImplTest {

    @InjectMocks
    private BoardRepositoryImpl boardRepository;

    @Mock
    private EntityManager em;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void save(){
        Board board = new Board();
        board.setTitle("Test Title");
        board.setComment("Test Comment");

        boardRepository.save(board);

        verify(em, times(1)).persist(board);
    }
    @Test
    public void findById(){
        Board board1 = new Board();
        board1.setId(1L);
        board1.setTitle("Test1 Title");
        board1.setComment("Test1 Comment");
        boardRepository.save(board1);

        when(em.find(Board.class, 1L)).thenReturn(board1);
        Board result = boardRepository.findById(1L);

        verify(em, times(1)).find(Board.class, 1L);

        assertThat(result).isEqualTo(board1);
    }
    @Test
    public void findAll(){
        Board board1 = new Board();
        board1.setTitle("Test1 Title");
        board1.setComment("Test1 Comment");

        Board board2 = new Board();
        board2.setTitle("Test2 Title");
        board2.setComment("Test2 Comment");

        List<Board> mockResult = Arrays.asList(board1, board2);

        TypedQuery<Board> mockTypedQuery = mock(TypedQuery.class);
        when(mockTypedQuery.getResultList()).thenReturn(mockResult);

        when(em.createQuery("select b from Board b", Board.class)).thenReturn(mockTypedQuery);

        List<Board> result = boardRepository.findAll();

        verify(em, times(1)).createQuery("select b from Board b", Board.class);
        assertThat(result).isEqualTo(mockResult);

    }
    @Test
    public void deleteById(){
        Board board1 = new Board();
        board1.setId(1L);
        board1.setTitle("Test1 Title");
        board1.setComment("Test1 Comment");

        when(em.find(Board.class, 1L)).thenReturn(board1);
        boardRepository.deleteById(1L);
        verify(em, times(1)).find(Board.class, 1L);
        verify(em, times(1)).remove(board1);
    }
    @Test
    public void update(){
        Board board1 = new Board();
        board1.setTitle("Test1 Title");
        board1.setComment("Test1 Comment");

        boardRepository.update(board1);
        verify(em, times(1)).merge(board1);
    }
}
