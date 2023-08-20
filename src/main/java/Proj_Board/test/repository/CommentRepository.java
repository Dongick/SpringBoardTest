package Proj_Board.test.repository;

import Proj_Board.test.model.Board;
import Proj_Board.test.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    void save(Comment comment);
    Optional<Comment> findById(Long id);
    List<Comment> findAll(Board board);
    void deleteById(Long id);
    void update(Comment comment);
}
