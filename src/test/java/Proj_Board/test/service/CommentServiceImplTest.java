package Proj_Board.test.service;

import Proj_Board.test.model.Board;
import Proj_Board.test.model.Comment;
import Proj_Board.test.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void upload() {
        Comment comment = new Comment();
        comment.setContent("Test comment");

        commentService.upload(comment);

        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void findAllComments() {
        Board board = new Board();
        board.setId(1L);

        List<Comment> comments = new ArrayList<>();
        Comment comment1 = new Comment();
        comment1.setId(1L);
        comment1.setContent("Comment 1");
        comment1.setBoard(board);

        Comment comment2 = new Comment();
        comment2.setId(1L);
        comment2.setContent("Comment 2");
        comment2.setBoard(board);

        comments.add(comment1);
        comments.add(comment2);

        when(commentRepository.findAll(board)).thenReturn(comments);

        List<Comment> result = commentService.findAllComments(board);
        assertEquals(2, result.size());
        assertEquals("Comment 1", result.get(0).getContent());
        assertEquals("Comment 2", result.get(1).getContent());
    }

    @Test
    void findOneComment() {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setContent("Test Comment");

        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        Comment result = commentService.findOneComment(1L);
        assertEquals("Test Comment", result.getContent());
    }

    @Test
    void delete() {
        Long commentId = 1L;

        commentService.delete(commentId);

        verify(commentRepository, times(1)).deleteById(commentId);
    }

    @Test
    void update() {
        Comment comment = new Comment();
        comment.setContent("Test comment");

        commentService.update(comment);

        verify(commentRepository, times(1)).update(comment);
    }
}
