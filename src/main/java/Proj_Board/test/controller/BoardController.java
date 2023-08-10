package Proj_Board.test.controller;

import Proj_Board.test.model.Board;
import Proj_Board.test.model.User;
import Proj_Board.test.service.BoardService;
import Proj_Board.test.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;

    @Autowired
    public BoardController(BoardService boardService, UserService userService) {
        this.boardService = boardService;
        this.userService = userService;
    }

    @GetMapping
    public String getBoardList(Model model, HttpSession session){
        List<Board> boards = boardService.findAllBoards();

        model.addAttribute("boardList", boards);

        String userId = (String) session.getAttribute("userId");
        if (userId != null) {
            model.addAttribute("userId", userId);
        }

        return "home";
    }

    @GetMapping("/{id}")
    public String getBoard(@PathVariable Long id, Model model, HttpSession session){
        String userId = (String) session.getAttribute("userId");
        if (userId != null) {
            model.addAttribute("userId", userId);
        }
        Board board = boardService.findOneBoard(id);

        if (board.getUser().getUserId().equals(userId)) {
            board.setCanEditAndDelete(true);
        }

        model.addAttribute("board", board);
        return "detail";
    }

    @GetMapping("/upload")
    public String getUploadBoard(HttpSession session, Model model){
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        model.addAttribute("userId", userId);

        return "upload";
    }

    @PostMapping("/upload")
    public String postUploadBoard(@RequestParam String title, @RequestParam String comment, HttpSession session, Model model){
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        User user = userService.findByUserId(userId);

        Board board = new Board();
        board.setTitle(title);
        board.setComment(comment);
        board.setUser(user);

        boardService.upload(board);

        model.addAttribute("userId", userId);

        return "redirect:/board";
    }

    @GetMapping("/update/{id}")
    public String getUpdateBoard(@PathVariable Long id, Model model, HttpSession session){
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        model.addAttribute("userId", userId);

        Board board = boardService.findOneBoard(id);
        if(board.getUser().getUserId().equals(userId)){
            model.addAttribute("board", board);
            return "update";
        } else{
            return "redirect:/board";
        }

    }

    @PostMapping("/update")
    public String postUpdateBoard(@RequestParam Long id, @RequestParam String title, @RequestParam String comment, HttpSession session, Model model){
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        model.addAttribute("userId", userId);

        Board board = boardService.findOneBoard(id);

        board.setTitle(title);
        board.setComment(comment);

        boardService.update(board);

        return "redirect:/board";
    }

    @PostMapping("/delete")
    public String postDeleteBoard(@RequestParam Long id, HttpSession session, Model model){
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        boardService.delete(id);

        model.addAttribute("userId", userId);

        return "redirect:/board";
    }
}
