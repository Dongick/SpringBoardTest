package Proj_Board.test.controller;

import Proj_Board.test.model.Board;
import Proj_Board.test.model.Comment;
import Proj_Board.test.model.User;
import Proj_Board.test.service.BoardService;
import Proj_Board.test.service.CommentService;
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
    private final CommentService commentService;

    @Autowired
    public BoardController(BoardService boardService, UserService userService, CommentService commentService) {
        this.boardService = boardService;
        this.userService = userService;
        this.commentService = commentService;
    }

    // 전체 게시판 화면
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

    // 게시판 상세 화면
    @GetMapping("/{id}")
    public String getBoardDetail(@PathVariable Long id, Model model, HttpSession session){
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

    // 게시판 업로드 화면
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
    public String postUploadBoard(@RequestParam String title, @RequestParam String detail, HttpSession session, Model model){
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        User user = userService.findByUserId(userId);

        Board board = new Board();
        board.setTitle(title);
        board.setDetail(detail);
        board.setUser(user);

        boardService.upload(board);

        //model.addAttribute("userId", userId);

        return "redirect:/board";
    }

    // 게시판 수정 화면
    @GetMapping("/update/{id}")
    public String getUpdateBoard(@PathVariable Long id, Model model, HttpSession session){
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Board board = boardService.findOneBoard(id);
        if(board.getUser().getUserId().equals(userId)){
            model.addAttribute("userId", userId);
            model.addAttribute("board", board);
            return "update";
        } else{
            return "redirect:/board";
        }

    }

    @PostMapping("/update")
    public String postUpdateBoard(@RequestParam Long id, @RequestParam String title, @RequestParam String detail, HttpSession session, Model model){
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        //model.addAttribute("userId", userId);

        Board board = boardService.findOneBoard(id);

        board.setTitle(title);
        board.setDetail(detail);

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

        //model.addAttribute("userId", userId);

        return "redirect:/board";
    }

    @PostMapping("/comment/add")
    public String postAddComment(@RequestParam Long postId, @RequestParam String content, HttpSession session){
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        User user = userService.findByUserId(userId);
        Board board = boardService.findOneBoard(postId);

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setBoard(board);

        commentService.upload(comment);

        return "redirect:/board/" + postId;
    }
}
