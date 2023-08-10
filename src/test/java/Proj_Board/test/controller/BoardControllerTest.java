package Proj_Board.test.controller;

import Proj_Board.test.model.Board;
import Proj_Board.test.model.User;
import Proj_Board.test.service.BoardService;
import Proj_Board.test.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@WebMvcTest(BoardController.class)
public class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardService boardService;

    @MockBean
    private UserService userService;

    @Test
    public void testGetBoardList() throws Exception {
        Board board1 = new Board();
        board1.setId(1L);
        board1.setTitle("Test1 Title");
        board1.setComment("Test1 Comment");

        Board board2 = new Board();
        board2.setId(2L);
        board2.setTitle("Test2 Title");
        board2.setComment("Test2 Comment");

        List<Board> boardList = new ArrayList<>();
        boardList.add(board1);
        boardList.add(board2);

        when(boardService.findAllBoards()).thenReturn(boardList);

        mockMvc.perform(MockMvcRequestBuilders.get("/board"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("home"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("boardList"));
    }

    @Test
    public void testGetBoardDetail() throws Exception {
        // Test setup
        Board board1 = new Board();
        board1.setId(1L);
        board1.setTitle("Test1 Title");
        board1.setComment("Test1 Comment");

        String userId = "testUser";

        User user = new User();
        user.setUserId(userId);
        board1.setUser(user);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", userId);

        when(boardService.findOneBoard(1L)).thenReturn(board1);

        mockMvc.perform(MockMvcRequestBuilders.get("/board/{id}", 1L).session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("detail"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("board"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("userId"))
                .andExpect(MockMvcResultMatchers.model().attribute("board", board1))
                .andExpect(MockMvcResultMatchers.model().attribute("userId", userId));
    }

    @Test
    public void testGetUploadBoard() throws Exception {

        String userId = "testUser";

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", userId);

        mockMvc.perform(MockMvcRequestBuilders.get("/board/upload").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("upload"));
    }

    @Test
    public void testPostUploadBoard() throws Exception {

        String userId = "testUser";
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", userId);

        when(userService.findByUserId(userId)).thenReturn(new User());

        mockMvc.perform(MockMvcRequestBuilders.post("/board/upload")
                .param("title", "Test Title")
                .param("comment", "Test Comment")
                .session(session))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/board"));
    }

    @Test
    public void testGetUpdateBoard() throws Exception {
        // Test setup
        Board board1 = new Board();
        board1.setId(1L);
        board1.setTitle("Test1 Title");
        board1.setComment("Test1 Comment");

        String userId = "testUser";
        User user = new User();
        user.setUserId(userId);

        board1.setUser(user);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", userId);

        when(boardService.findOneBoard(1L)).thenReturn(board1);

        mockMvc.perform(MockMvcRequestBuilders.get("/board/update/{id}", 1L).session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("update"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("board"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("userId"))
                .andExpect(MockMvcResultMatchers.model().attribute("board", board1))
                .andExpect(MockMvcResultMatchers.model().attribute("userId", userId));
    }

    @Test
    public void testPostUpdateBoard() throws Exception {
        // Test setup
        Board board1 = new Board();
        board1.setId(1L);
        board1.setTitle("Test1 Title");
        board1.setComment("Test1 Comment");

        String userId = "testUser";
        User user = new User();
        user.setUserId(userId);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", userId);

        board1.setUser(user);

        when(boardService.findOneBoard(1L)).thenReturn(board1);

        mockMvc.perform(MockMvcRequestBuilders.post("/board/update")
                        .session(session)
                        .param("id", "1")
                        .param("title", "Updated Title")
                        .param("comment", "Updated Comment"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/board"));

        verify(boardService, times(1)).update(board1);
    }

    @Test
    public void testPostDeleteBoard() throws Exception {

        Long boardId = 1L;
        String userId = "testUser";

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", userId);

        mockMvc.perform(MockMvcRequestBuilders.post("/board/delete")
                        .session(session)
                        .param("id", "1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/board"));

        verify(boardService, times(1)).delete(boardId);
    }
}
