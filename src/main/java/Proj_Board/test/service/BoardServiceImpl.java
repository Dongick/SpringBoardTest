package Proj_Board.test.service;

import Proj_Board.test.model.Board;
import Proj_Board.test.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public List<Board> findAllBoards() {
        return boardRepository.findAll();
    }

    @Override
    public Board findOneBoard(Long id) {
        return boardRepository.findById(id);
    }

    @Override
    public void upload(Board board) {
        boardRepository.save(board);
    }

    @Override
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    @Override
    public void update(Board board){
        boardRepository.update(board);
    }
}
