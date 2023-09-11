package Proj_Board.test.service;

import Proj_Board.test.model.ChildrenComment;
import Proj_Board.test.model.Comment;

import java.util.List;

public interface ChildrenCommentService {
    void upload(ChildrenComment comment);
    List<ChildrenComment> findAllComments(Comment comment);
    ChildrenComment findOneComment(Long id);
    void delete(Long id);
    void update(ChildrenComment comment);
}
