package Proj_Board.test.repository;

import Proj_Board.test.model.ChildrenComment;
import Proj_Board.test.model.Comment;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ChildrenCommentRepositoryImpl implements ChildrenCommentRepository{

    private final EntityManager em;

    @Autowired
    public ChildrenCommentRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(ChildrenComment comment) {
        em.persist(comment);
    }

    @Override
    public Optional<ChildrenComment> findById(Long id) {
        return Optional.ofNullable(em.find(ChildrenComment.class, id));
    }

    @Override
    public List<ChildrenComment> findAll(Comment comment) {
        return em.createQuery("SELECT C FROM ChildrenComment C WHERE C.commentId = :comment", ChildrenComment.class)
                .setParameter("comment", comment)
                .getResultList();
    }

    @Override
    public void deleteById(Long id) {
        ChildrenComment comment = em.find(ChildrenComment.class, id);
        if(comment != null){
            em.remove(comment);
        }
    }

    @Override
    public void update(ChildrenComment comment) {
        em.merge(comment);
    }
}
