package com.cody.life.immutable;

import com.cody.life.immutable.board.LifeBoard;

public class Run {

    public static void main(String[] args) {

        int rows = 50;
        int cols = 50;

        if (args.length == 1) {
            rows = cols = Integer.parseInt(args[0]);
        } else if (args.length == 2) {
            rows = Integer.parseInt(args[0]);
            cols = Integer.parseInt(args[1]);
        }

        LifeBoard board = LifeBoard.random(rows, cols);

        // statistics used to continue or break
        //
        int numAlive = board.numAlive();
        int generation = 1;
        boolean same = false;

        while (numAlive > 0 && !same) {

            // Generate new generation and update stats
            //
            LifeBoard newGeneration = board.debugPrint().generate().printTiming("Time for generation " + generation++);

            same = board.isSameBoard(newGeneration);
            board = newGeneration;
            numAlive = board.numAlive();
        }

        if (same) {
            System.out.println("Detected identical generation at generation " + generation + "!");
            board.debugPrint();
        } else if (numAlive == 0) {
            System.out.println("Detected extinction at generation " + generation + "!");
            board.debugPrint();
        }
    }

    public static void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

}
