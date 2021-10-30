package com.cody.life;

import java.util.ArrayList;

public class Board {

    static Character CLEAR_CHAR = '.';
    static Character SPACE_CHAR = ' ';

    ArrayList<ArrayList<Character>> board;
    int rows;
    int cols;
    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        board = new ArrayList<>(rows);
        for (int i=0; i < rows; i++) {
            board.add(new ArrayList<>());
            for (int j=0; j < cols; j++) {
                board.get(i).add(CLEAR_CHAR);
            }
        }

        clear();
    }

    public void clear() {
        for (int i=0; i < rows; i++) {
            for (int j=0; j < cols; j++) {
                try {
                    board.get(i).set(j, CLEAR_CHAR);
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void print() {
        for (int i=0; i < rows; i++) {
            for (int j=0; j < cols; j++) {
                System.out.print(board.get(i).get(j));
                System.out.print(SPACE_CHAR);
            }
            System.out.println();
        }
    }
}
