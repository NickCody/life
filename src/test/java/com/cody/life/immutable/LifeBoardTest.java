package com.cody.life.immutable;

import com.cody.life.immutable.board.LifeBoard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class LifeBoardTest {
    @Test
    void createTest() {
        LifeBoard board = LifeBoard.random(3, 4);
        Assertions.assertEquals( 3, board.getNumRows() );
        Assertions.assertEquals( 4, board.getNumCols() );
        Assertions.assertEquals(12, board.getBufferLength() );
    }

}
