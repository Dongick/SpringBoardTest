package Proj_Board.test.repository;

import Proj_Board.test.model.Board;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BoardRepositoryImpl implements BoardRepository{

    private final EntityManager em;

    @Autowired
    public BoardRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(Board board) {
        em.persist(board);
    }

    @Override
    public Optional<Board> findById(Long id) {
        return Optional.ofNullable(em.find(Board.class, id));
    }

    @Override
    public List<Board> findAll() {
        return em.createQuery("select b from Board b", Board.class).getResultList();
    }

    @Override
    public void deleteById(Long id) {
        Board board = em.find(Board.class, id);
        if(board != null){
            em.remove(board);
        }
    }

    @Override
    public void update(Board board) {
        em.merge(board);
    }
}
