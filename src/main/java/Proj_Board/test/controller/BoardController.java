package Proj_Board.test.controller;

import Proj_Board.test.model.Board;
import Proj_Board.test.model.ChildrenComment;
import Proj_Board.test.model.Comment;
import Proj_Board.test.model.User;
import Proj_Board.test.service.BoardService;
import Proj_Board.test.service.ChildrenCommentService;
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
    private final ChildrenCommentService childrenCommentService;

    @Autowired
    public BoardController(BoardService boardService, UserService userService, CommentService commentService, ChildrenCommentService childrenCommentService) {
        this.boardService = boardService;
        this.userService = userService;
        this.commentService = commentService;
        this.childrenCommentService = childrenCommentService;
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

        for(Comment comment : board.getComments()){
            if (comment.getUser().getUserId().equals(userId)) {
                comment.setCanEditAndDelete(true);
            }
            for(ChildrenComment childrenComment : comment.getChildrenComments()){
                if (childrenComment.getUser().getUserId().equals((userId))) {
                    childrenComment.setCanEditAndDelete(true);
                }
            }
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

    // 게시판 업로드
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

    // 게시판 수정
    @PostMapping("/update")
    public String postUpdateBoard(@RequestParam Long id, @RequestParam String title, @RequestParam String detail, HttpSession session, Model model){
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Board board = boardService.findOneBoard(id);

        board.setTitle(title);
        board.setDetail(detail);

        boardService.update(board);

        return "redirect:/board";
    }

    // 게시판 삭제
    @PostMapping("/delete")
    public String postDeleteBoard(@RequestParam Long id, HttpSession session, Model model){
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        boardService.delete(id);

        return "redirect:/board";
    }

    // 게시판 댓글 추가
    @PostMapping("/comment/add")
    public String postAddComment(@RequestParam Long id, @RequestParam String content, HttpSession session){
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        User user = userService.findByUserId(userId);
        Board board = boardService.findOneBoard(id);

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setBoard(board);

        commentService.upload(comment);

        return "redirect:/board/" + id;
    }

    // 게시판 댓글 삭제
    @PostMapping("/comment/delete")
    public String postDeleteComment(@RequestParam Long id, @RequestParam Long commentDeleteId, HttpSession session){
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        commentService.delete(commentDeleteId);

        return "redirect:/board/" + id;
    }

    // 게시판 댓글 수정
//    @PostMapping("/comment/update/{id}")
//    public String postUpdateComment(@PathVariable Long id, HttpSession session){
//        String userId = (String) session.getAttribute("userId");
//        if (userId == null) {
//            return "redirect:/login";
//        }
//
//        Comment comment = commentService.findOneComment(id);
//        comment.set
//
//        return "redirect:/board/" + id;
//    }

    // 게시판 대댓글 추가
    @PostMapping("/childrenComment/add")
    public String postAddChildrenComment(@RequestParam Long id, @RequestParam Long parentCommentId, @RequestParam String content, HttpSession session){
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        User user = userService.findByUserId(userId);
        Comment comment = commentService.findOneComment(parentCommentId);

        ChildrenComment childrenComment = new ChildrenComment();
        childrenComment.setContent(content);
        childrenComment.setUser(user);
        childrenComment.setComment(comment);

        childrenCommentService.upload(childrenComment);

        return "redirect:/board/" + id;
    }

    // 게시판 대댓글 삭제
    @PostMapping("/childrenComment/delete")
    public String postDeleteChildrenComment(@RequestParam Long id, @RequestParam Long childrenCommentDeleteId, HttpSession session){
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        childrenCommentService.delete(childrenCommentDeleteId);

        return "redirect:/board/" + id;
    }
}
