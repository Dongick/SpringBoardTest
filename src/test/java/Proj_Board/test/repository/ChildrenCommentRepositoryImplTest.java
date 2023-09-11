package Proj_Board.test.repository;

import Proj_Board.test.model.ChildrenComment;
import Proj_Board.test.model.Comment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ChildrenCommentRepositoryImplTest {
    @InjectMocks
    private ChildrenCommentRepositoryImpl childrenCommentRepository;

    @Mock
    private EntityManager em;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void save(){
        ChildrenComment childrenComment = new ChildrenComment();
        childrenComment.setContent("Test Comment");

        childrenCommentRepository.save(childrenComment);

        verify(em, times(1)).persist(childrenComment);
    }

    @Test
    public void findById(){
        Long commentId = 1L;
        ChildrenComment childrenComment = new ChildrenComment();
        childrenComment.setId(commentId);
        childrenComment.setContent("Test Comment");

        when(em.find(ChildrenComment.class, commentId)).thenReturn(childrenComment);

        Optional<ChildrenComment> foundComment = childrenCommentRepository.findById(commentId);

        assertTrue(foundComment.isPresent());
        assertEquals(commentId, foundComment.get().getId());
    }

    @Test
    public void findAll(){
        Comment comment = new Comment();
        comment.setId(1L);
        ChildrenComment childrenComment1 = new ChildrenComment();
        childrenComment1.setContent("Comment 1");
        childrenComment1.setComment(comment);
        ChildrenComment childrenComment2 = new ChildrenComment();
        childrenComment2.setContent("Comment 2");
        childrenComment2.setComment(comment);

        List<ChildrenComment> mockResult = Arrays.asList(childrenComment1, childrenComment2);

        TypedQuery<ChildrenComment> query = mock(TypedQuery.class);
        when(query.setParameter(eq("comment"), eq(comment))).thenReturn(query);
        when(query.getResultList()).thenReturn(mockResult);

        when(em.createQuery("SELECT C FROM ChildrenComment C WHERE C.commentId = :comment", ChildrenComment.class)).thenReturn(query);

        List<ChildrenComment> childrenComments = childrenCommentRepository.findAll(comment);

        verify(em, times(1)).createQuery("SELECT C FROM ChildrenComment C WHERE C.commentId = :comment", ChildrenComment.class);
        verify(query, times(1)).setParameter("comment", comment);
        assertEquals(2, childrenComments.size());
        assertThat(childrenComments).isEqualTo(mockResult);
    }

    @Test
    public void deleteById(){
        Long commentId = 1L;
        ChildrenComment comment = new ChildrenComment();
        comment.setId(commentId);

        when(em.find(ChildrenComment.class, commentId)).thenReturn(comment);

        childrenCommentRepository.deleteById(commentId);

        verify(em, times(1)).remove(comment);
    }

    @Test
    public void update(){
        Long commentId = 1L;
        ChildrenComment comment = new ChildrenComment();
        comment.setId(commentId);
        comment.setContent("Test Comment");

        childrenCommentRepository.update(comment);

        verify(em, times(1)).merge(comment);
    }
}
