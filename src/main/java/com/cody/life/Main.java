package com.cody.life;

public class Main {

    public static void main(String[] args) {
        Board board = new Board(20,20);
        board.random();
        System.out.println("This is the random board");
        board.print();

        for (int i=0; i < 10; i++) {
            board.newGeneration();
            System.out.println("This is the random board with game of life applied");
            board.print();
        }

    }
}
