package Proj_Board.test.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class boardTest {
    @Test
    void createBoard(){
        Board board = new Board();
        board.setTitle("test");
        board.setComment("test 중입니다");
        assertThat(board.getTitle()).isEqualTo("test");
        assertThat(board.getComment()).isEqualTo("test 중입니다");
    }
}
