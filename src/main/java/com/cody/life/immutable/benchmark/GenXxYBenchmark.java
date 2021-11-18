package com.cody.life.immutable.benchmark;

import com.cody.life.immutable.board.LifeBoard;

public class GenXxYBenchmark {
    public static void main(String[] args) {

        int rows = 100;
        int cols = 100;
        long maxIterations = 3000;

        if (args.length == 1) {
            rows = cols = Integer.parseInt(args[0]);
        } else if (args.length == 2) {
            rows = Integer.parseInt(args[0]);
            cols = Integer.parseInt(args[1]);
        } else if (args.length == 3) {
            rows = Integer.parseInt(args[0]);
            cols = Integer.parseInt(args[1]);
            maxIterations = Integer.parseInt(args[2]);
        }

        long timeBefore = System.nanoTime();
        LifeBoard board = LifeBoard.random(rows, cols);
        for (long i=0; i < maxIterations; i++) {
            LifeBoard temp = board.generate();
            if (temp.numAlive() == 0 || board.isSameBoard(temp))
                board = LifeBoard.random(rows, cols);
            else
                board = temp;
        }
        long timeAfter = System.nanoTime();
        long durationMicros = (timeAfter - timeBefore)/1000;
        System.out.println("Time in micros: " + durationMicros + " (" + durationMicros / 1000000.0 + " seconds)");
    }
}
