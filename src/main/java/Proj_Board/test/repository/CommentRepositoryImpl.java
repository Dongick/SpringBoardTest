package Proj_Board.test.repository;

import Proj_Board.test.model.Board;
import Proj_Board.test.model.Comment;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepositoryImpl implements CommentRepository{
    private final EntityManager em;

    @Autowired
    public CommentRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(Comment comment) {
        em.persist(comment);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> findAll(Board board) {
        return em.createQuery("SELECT C FROM Comment C WHERE C.boardId = :board", Comment.class)
                .setParameter("board", board)
                .getResultList();
    }

    @Override
    public void deleteById(Long id) {
        Comment comment = em.find(Comment.class, id);
        if(comment != null){
            em.remove(comment);
        }
    }

    @Override
    public void update(Comment comment) {
        em.merge(comment);
    }
}
