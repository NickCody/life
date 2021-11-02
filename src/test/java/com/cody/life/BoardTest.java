package com.cody.life;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class BoardTest {
    @Test
    void createTest() {
        Board board = new Board(3,4);
        Assertions.assertEquals( board.rows,3 );
        Assertions.assertEquals( board.cols,4 );
    }
}


