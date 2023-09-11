package Proj_Board.test.service;

import Proj_Board.test.model.ChildrenComment;
import Proj_Board.test.model.Comment;
import Proj_Board.test.repository.ChildrenCommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildrenCommentServiceImpl implements ChildrenCommentService{
    private final ChildrenCommentRepository childrenCommentRepository;

    public ChildrenCommentServiceImpl(ChildrenCommentRepository childrenCommentRepository) {
        this.childrenCommentRepository = childrenCommentRepository;
    }

    @Override
    @Transactional
    public void upload(ChildrenComment comment) {
        childrenCommentRepository.save(comment);
    }

    @Override
    public List<ChildrenComment> findAllComments(Comment comment) {
        return childrenCommentRepository.findAll(comment);
    }

    @Override
    public ChildrenComment findOneComment(Long id) {
        return childrenCommentRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        childrenCommentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(ChildrenComment comment) {
        childrenCommentRepository.update(comment);
    }
}
