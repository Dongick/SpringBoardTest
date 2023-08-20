package Proj_Board.test.service;

import Proj_Board.test.model.Board;
import Proj_Board.test.repository.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BoardServiceTest {
    @InjectMocks
    private BoardServiceImpl boardService;

    @Mock
    private BoardRepository mockBoardRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAllBoards(){
        List<Board> mockBoards = new ArrayList<>();
        mockBoards.add(new Board());
        mockBoards.add(new Board());

        when(mockBoardRepository.findAll()).thenReturn(mockBoards);

        List<Board> result = boardService.findAllBoards();

        assertThat(result.size()).isEqualTo(2);
        verify(mockBoardRepository, times(1)).findAll();
    }

    @Test
    public void findOneBoard(){
        Board mockBoard = new Board();
        mockBoard.setId(1L);
        mockBoard.setTitle("Test Title");
        mockBoard.setDetail("Test Detail");

        when(mockBoardRepository.findById(1L)).thenReturn(Optional.of(mockBoard));

        Board result = boardService.findOneBoard(1L);

        assertThat(result).isEqualTo(mockBoard);
        verify(mockBoardRepository, times(1)).findById(1L);
    }

    @Test
    public void upload(){
        Board board = new Board();
        board.setTitle("Test Title");
        board.setDetail("Test Detail");

        boardService.upload(board);

        verify(mockBoardRepository, times(1)).save(board);
    }

    @Test
    public void delete(){
        Long boardId = 1L;

        boardService.delete(boardId);

        verify(mockBoardRepository, times(1)).deleteById(boardId);
    }

    @Test
    public void update(){
        Board board = new Board();
        board.setTitle("Test Title");
        board.setDetail("Test Detail");

        boardService.update(board);

        verify(mockBoardRepository, times(1)).update(board);
    }
}
