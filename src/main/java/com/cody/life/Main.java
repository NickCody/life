package com.cody.life;

public class Main {

    public static void main(String[] args) {

        int rows = 20;
        int cols = 20;
        int maxGenerations = 0;

        if (args.length == 1) {
            rows = cols = Integer.parseInt(args[0]);
        } else if (args.length == 2) {
            rows = Integer.parseInt(args[0]);
            cols = Integer.parseInt(args[1]);
        }
          else if (args.length == 3) {
            rows = Integer.parseInt(args[0]);
            cols = Integer.parseInt(args[1]);
            maxGenerations = Integer.parseInt(args[2]);
        }

        Board board = new Board(rows,cols);
        board.random();
        System.out.println("This is the random board");
        board.print();

        int numAlive = board.numAlive();
        int generationCount = 0;
        boolean sameBoard = false;

        while (numAlive > 0 && !sameBoard && generationCount <= maxGenerations) {
            Board nextBoard = board.newGeneration();
            System.out.println("This is generation number " + generationCount);
            board.print();

            sameBoard = board.isSameBoard(nextBoard);
            board = nextBoard;
            numAlive = board.numAlive();

            generationCount++;
        }

        if (sameBoard) {
            System.out.println("Detected identical generation at generation " + generationCount + "!");
            board.print();
        } else if (numAlive == 0) {
            System.out.println("Detected extinction at generation " + generationCount + "!");
            board.print();
        }
          else if (generationCount > maxGenerations) {
            System.out.println("Max generations has been breached at " + generationCount + "!");
            board.print();
        }





    }
}
