package Proj_Board.test.service;

import Proj_Board.test.model.Board;
import Proj_Board.test.model.ChildrenComment;
import Proj_Board.test.model.Comment;
import Proj_Board.test.repository.ChildrenCommentRepository;
import Proj_Board.test.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ChildrenCommentServiceImplTest {
    @InjectMocks
    private ChildrenCommentServiceImpl commentService;

    @Mock
    private ChildrenCommentRepository commentRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void upload() {
        ChildrenComment comment = new ChildrenComment();
        comment.setContent("Test comment");

        commentService.upload(comment);

        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void findAllComments() {
        Comment comment = new Comment();
        comment.setId(1L);

        List<ChildrenComment> comments = new ArrayList<>();
        ChildrenComment childrenComment1 = new ChildrenComment();
        childrenComment1.setId(1L);
        childrenComment1.setContent("Comment 1");
        childrenComment1.setComment(comment);

        ChildrenComment childrenComment2 = new ChildrenComment();
        childrenComment2.setId(1L);
        childrenComment2.setContent("Comment 2");
        childrenComment2.setComment(comment);

        comments.add(childrenComment1);
        comments.add(childrenComment2);

        when(commentRepository.findAll(comment)).thenReturn(comments);

        List<ChildrenComment> result = commentService.findAllComments(comment);
        assertEquals(2, result.size());
        assertEquals("Comment 1", result.get(0).getContent());
        assertEquals("Comment 2", result.get(1).getContent());
    }

    @Test
    void findOneComment() {
        ChildrenComment childrenComment = new ChildrenComment();
        childrenComment.setId(1L);
        childrenComment.setContent("Test Comment");

        when(commentRepository.findById(1L)).thenReturn(Optional.of(childrenComment));

        ChildrenComment result = commentService.findOneComment(1L);
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
        ChildrenComment childrenComment = new ChildrenComment();
        childrenComment.setContent("Test Comment");

        commentService.update(childrenComment);

        verify(commentRepository, times(1)).update(childrenComment);
    }
}
