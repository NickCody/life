package com.cody.life;

import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

public class Board {

    static Character DEAD_CHAR = '·';
    static Character SPACE_CHAR = ' ';
    static Character ALIVE_CHAR = '⬤';

    ArrayList<ArrayList<Character>> board;
    int rows;
    int cols;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        board = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            board.add(new ArrayList<>());
            for (int j = 0; j < cols; j++) {
                board.get(i).add(DEAD_CHAR);
            }
        }

        clear();
    }

    public void clear() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                try {
                    board.get(i).set(j, DEAD_CHAR);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void print() {
        Charset utf8 = StandardCharsets.UTF_8;
        PrintWriter printWriter = new PrintWriter(System.out,true, utf8);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                printWriter.print(board.get(i).get(j));
                printWriter.print(SPACE_CHAR);
            }
            printWriter.println();
        }
    }

    public void setPosition(int row, int col, Character place){
        board.get(row).set(col, place);
    }

    // public random
    public void random() {
        Random rand = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (rand.nextInt() % 2 == 1) {
                    board.get(i).set(j, ALIVE_CHAR);
                } else {
                    board.get(i).set(j, DEAD_CHAR);
                }
            }
        }

    }

    boolean isValidPosition(int row, int col){
        return row >= 0 && col >= 0 && row < rows && col < cols;
    }

    Character safeGet(int row, int col){
        if (isValidPosition(row, col)) {
            return board.get(row).get(col);
        }
        else{
            return DEAD_CHAR;
        }
    }

    public void debugPrint() {
        Charset utf8 = StandardCharsets.UTF_8;
        PrintWriter printWriter = new PrintWriter(System.out,true, utf8);
        Character[] liveNeighbors = { '⓿', '➊', '➋', '➌', '➍', '➎', '➏', '➐', '➑' };
        Character[] deadNeighbors = { '⓪', '➀', '➁', '➂', '➃', '➄', '➅', '➆', '➇' };

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Character c;
                if (safeGet(i, j) == ALIVE_CHAR) {
                    c = liveNeighbors[countNeighbors(i,j)];
                } else {
                    c = deadNeighbors[countNeighbors(i,j)];
                }
                printWriter.print(c);
                printWriter.print(" ");
            }
            printWriter.println();
        }

    }

    int countNeighbors(int row, int col){
        int count = 0;
        if (safeGet(row -1, col -1) == ALIVE_CHAR) {
            ++count;
        }
        if (safeGet(row, col -1) == ALIVE_CHAR) {
            ++count;
        }
        if (safeGet(row + 1, col - 1) == ALIVE_CHAR) {
            ++count;
        }
        if (safeGet(row + 1, col) == ALIVE_CHAR) {
            ++count;
        }
        if (safeGet(row +1, col +1) == ALIVE_CHAR) {
            ++count;
        }
        if (safeGet(row, col +1) == ALIVE_CHAR) {
            ++count;
        }
        if (safeGet(row -1, col +1) == ALIVE_CHAR) {
            ++count;
        }
        if (safeGet(row -1, col) == ALIVE_CHAR) {
            ++count;
        }
        return count;
    }

    public Board newGeneration(){
        //Creates a new random board
        Board newBoard = new Board(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (safeGet(i, j) == ALIVE_CHAR){
                    if (countNeighbors(i, j) == 2 || countNeighbors(i, j) == 3){
                        newBoard.setPosition(i,j, ALIVE_CHAR);
                    }
                    else{
                        newBoard.setPosition(i,j, DEAD_CHAR);
                    }
                }
                else{
                    if (countNeighbors(i, j) == 3){
                        newBoard.setPosition(i,j, ALIVE_CHAR);
                    }
                    else{
                        newBoard.setPosition(i,j, DEAD_CHAR);
                    }
                }
            }
        }
        return newBoard;
    }

    public boolean isSameBoard (Board toCompare) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board.get(i).get(j) != toCompare.board.get(i).get(j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public int numAlive() {
        int aliveSpaces = 0;
        for (int i = 0; i < rows ; i++) {
            for (int j = 0; j < cols; j++) {
                if (board.get(i).get(j) == ALIVE_CHAR) {
                    aliveSpaces++;
                }
            }
        }
        return aliveSpaces;
    }

}




