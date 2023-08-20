package Proj_Board.test.service;

import Proj_Board.test.model.Board;
import Proj_Board.test.model.Comment;
import Proj_Board.test.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public void upload(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> findAllComments(Board board) {
        return commentRepository.findAll(board);
    }

    @Override
    public Comment findOneComment(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Comment comment) {
        commentRepository.update(comment);
    }
}
