package Proj_Board.test.controller;

import Proj_Board.test.model.Board;
import Proj_Board.test.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(BoardController.class)
public class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardService boardService;

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

        when(boardService.findOneBoard(1L)).thenReturn(board1);

        mockMvc.perform(MockMvcRequestBuilders.get("/board/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("detail"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("board"));
    }

    @Test
    public void testGetUploadBoard() throws Exception {
        // Test setup

        mockMvc.perform(MockMvcRequestBuilders.get("/board/upload"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("upload"));
    }

    @Test
    public void testPostUploadBoard() throws Exception {
        // Test setup

        mockMvc.perform(MockMvcRequestBuilders.post("/board/upload")
                        .param("title", "Test Title")
                        .param("comment", "Test Comment"))
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

        when(boardService.findOneBoard(1L)).thenReturn(board1);

        mockMvc.perform(MockMvcRequestBuilders.get("/board/update/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("update"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("board"));
    }

    @Test
    public void testPostUpdateBoard() throws Exception {
        // Test setup
        Board board1 = new Board();
        board1.setId(1L);
        board1.setTitle("Test1 Title");
        board1.setComment("Test1 Comment");

        when(boardService.findOneBoard(1L)).thenReturn(board1);

        mockMvc.perform(MockMvcRequestBuilders.post("/board/update")
                        .param("id", "1")
                        .param("title", "Updated Title")
                        .param("comment", "Updated Comment"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/board"));
    }

    @Test
    public void testPostDeleteBoard() throws Exception {
        // Test setup

        mockMvc.perform(MockMvcRequestBuilders.post("/board/delete")
                        .param("id", "1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/board"));
    }
}
