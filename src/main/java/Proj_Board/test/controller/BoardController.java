package Proj_Board.test.controller;

import Proj_Board.test.model.Board;
import Proj_Board.test.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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
        return "home";
    }

    @GetMapping("/{id}")
    public String getBoard(@PathVariable Long id, Model model){
        Board board = boardService.findOneBoard(id);
        model.addAttribute("board", board);
        return "detail";
    }

    @GetMapping("/upload")
    public String getUploadBoard(){
        return "upload";
    }

    @PostMapping("/upload")
    public String postUploadBoard(@RequestParam String title, @RequestParam String comment){
        Board board = new Board();
        board.setTitle(title);
        board.setComment(comment);

        boardService.upload(board);

        return "redirect:/board";
    }

    @GetMapping("/update/{id}")
    public String getUpdateBoard(@PathVariable Long id, Model model){
        Board board = boardService.findOneBoard(id);
        model.addAttribute("board", board);
        return "update";
    }

    @PostMapping("/update")
    public String postUpdateBoard(@RequestParam Long id, @RequestParam String title, @RequestParam String comment){
        Board board = boardService.findOneBoard(id);

        board.setTitle(title);
        board.setComment(comment);

        boardService.update(board);

        return "redirect:/board";
    }

    @PostMapping("/delete")
    public String postDeleteBoard(@RequestParam Long id){
        boardService.delete(id);

        return "redirect:/board";
    }
}
