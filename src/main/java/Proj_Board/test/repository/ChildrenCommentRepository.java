package Proj_Board.test.repository;

import Proj_Board.test.model.ChildrenComment;
import Proj_Board.test.model.Comment;

import java.util.List;
import java.util.Optional;

public interface ChildrenCommentRepository {
    void save(ChildrenComment comment);
    Optional<ChildrenComment> findById(Long id);
    List<ChildrenComment> findAll(Comment comment);
    void deleteById(Long id);
    void update(ChildrenComment comment);
}
