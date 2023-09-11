package Proj_Board.test.repository;

import Proj_Board.test.model.Board;
import Proj_Board.test.model.Comment;
import Proj_Board.test.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommentRepositoryImplTest {

    @InjectMocks
    private CommentRepositoryImpl commentRepository;

    @Mock
    private EntityManager em;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void save(){
        Comment comment = new Comment();
        comment.setContent("Test Comment");

        commentRepository.save(comment);

        verify(em, times(1)).persist(comment);
    }

    @Test
    public void findById(){
        Long commentId = 1L;
        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setContent("Test Comment");

        when(em.find(Comment.class, commentId)).thenReturn(comment);

        Optional<Comment> foundComment = commentRepository.findById(commentId);

        assertTrue(foundComment.isPresent());
        assertEquals(commentId, foundComment.get().getId());
    }

    @Test
    public void findAll(){
        Board board = new Board();
        board.setId(1L);
        Comment comment1 = new Comment();
        comment1.setContent("Comment 1");
        comment1.setBoard(board);
        Comment comment2 = new Comment();
        comment2.setContent("Comment 2");
        comment2.setBoard(board);

        List<Comment> mockResult = Arrays.asList(comment1, comment2);

        TypedQuery<Comment> query = mock(TypedQuery.class);
        when(query.setParameter(eq("board"), eq(board))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockResult);

        when(em.createQuery("SELECT C FROM Comment C WHERE C.boardId = :board", Comment.class)).thenReturn(query);

        List<Comment> comments = commentRepository.findAll(board);

        verify(em, times(1)).createQuery("SELECT C FROM Comment C WHERE C.boardId = :board", Comment.class);
        verify(query, times(1)).setParameter("board", board);
        assertEquals(2, comments.size());
        assertThat(comments).isEqualTo(mockResult);
    }

    @Test
    public void deleteById(){
        Long commentId = 1L;
        Comment comment = new Comment();
        comment.setId(commentId);

        when(em.find(Comment.class, commentId)).thenReturn(comment);

        commentRepository.deleteById(commentId);

        verify(em, times(1)).remove(comment);
    }

    @Test
    public void update(){
        Long commentId = 1L;
        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setContent("Test Comment");

        commentRepository.update(comment);

        verify(em, times(1)).merge(comment);
    }
}
