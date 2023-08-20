package Proj_Board.test.service;

import Proj_Board.test.model.Board;
import Proj_Board.test.model.Comment;

import java.util.List;

public interface CommentService {
    void upload(Comment comment);
    List<Comment> findAllComments(Board board);
    Comment findOneComment(Long id);
    void delete(Long id);
    void update(Comment comment);
}
