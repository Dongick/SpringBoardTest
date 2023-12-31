package Proj_Board.test.repository;

import Proj_Board.test.model.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    void save(Board board);
    Optional<Board> findById(Long id);
    List<Board> findAll();
    void deleteById(Long id);
    void update(Board board);
}
