package Proj_Board.test.controller;

import Proj_Board.test.model.Board;
import Proj_Board.test.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public String getBoardList(Model model){
        List<Board> boards = boardService.findAllBoards();
        model.addAttribute("boardList", boards);
        return "1";
    }
    @GetMapping("/{id}")
    public String getBoard(@PathVariable Long id, Model model){
        Board board = boardService.findOneBoard(id);
        model.addAttribute("board", board);
        return "2";
    }
}
