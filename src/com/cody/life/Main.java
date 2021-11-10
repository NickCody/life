package com.cody.life;

public class Main {

    public static void main(String[] args) {
        Board board = new Board(20,20);
        board.random();
        System.out.println("This is the random board");
        board.debugPrint();

        board.newGeneration();
        System.out.println("This is the random board with game of life applied");
        board.debugPrint();


    }
}
