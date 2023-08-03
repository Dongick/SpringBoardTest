package Proj_Board.test.service;

import Proj_Board.test.model.Board;
import java.util.List;

public interface BoardService {
    void upload(Board board);
    List<Board> findAllBoards();
    Board findOneBoard(Long id);
    void delete(Long id);
    void update(Board board);
}
