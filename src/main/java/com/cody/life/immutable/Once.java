package com.cody.life.immutable;

import com.cody.life.immutable.board.LifeBoard;

public class Once {
    public static void main(String[] args) {

        int rows = 5;
        int cols = 5;

        if (args.length == 1) {
            rows = cols = Integer.parseInt(args[0]);
        } else if (args.length == 2) {
            rows = Integer.parseInt(args[0]);
            cols = Integer.parseInt(args[1]);
        }

        LifeBoard.random(rows, cols)
            .debugPrint()
            .recordTiming()
            .generate()
            .printTiming("Since generate")
            .debugPrint();
    }
}
