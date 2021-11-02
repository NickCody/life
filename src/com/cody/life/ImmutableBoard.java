package com.cody.life;

import java.util.ArrayList;
import java.util.Random;

public class ImmutableBoard {

    static Character DEAD_CHAR = '⚬';
    static Character LIVE_CHAR = '⬤';

    ArrayList<Character> board;
    int numRows;
    int numCols;

    // Standard (rows, cols) constructor
    //
    public ImmutableBoard(int rows, int cols) {

        this.numRows = rows;
        this.numCols = cols;

        board = _allocate(numRows, numCols);
    }

    // Copy constructor, copy from one board to another
    //
    public ImmutableBoard(ImmutableBoard that) {

        this.numRows = that.numRows;
        this.numCols = that.numCols;

        board = _allocate(numRows, numCols);
        for (int i = 0; i < numRows * numCols; i++) {
            board.set(i, that.board.get(i));
        }
    }

    //
    // Statics -=-=-=-=-=-=-=-=-=-=-=-=-=-===-=-=-=-=
    //

    // create a new board
    //
    static ImmutableBoard create(int rows, int cols) {
        return new ImmutableBoard(rows, cols);
    }

    static ImmutableBoard random(int rows, int cols) {
        ImmutableBoard board = new ImmutableBoard(rows, cols);
        Random r = new Random();
        for (int i = 0; i < rows * cols; i++) {
            if (r.nextBoolean())
                board.set(i, LIVE_CHAR);
            else
                board.set(i, DEAD_CHAR);
        }

        return board;
    }

    private static ArrayList<Character> _allocate(int rows, int cols) {
        ArrayList<Character> _board = new ArrayList<>(rows * cols);

        for (int i = 0; i < rows * cols; i++) {
            _board.add(DEAD_CHAR);
        }
        return _board;
    }

    // printWithSeparator
    //
    public ImmutableBoard printWithSeparator() {
        print();
        for (int j = 0; j < numCols; j++) {
            System.out.print("--");
        }
        System.out.println();
        return this;
    }

    // print
    //
    public ImmutableBoard print() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                System.out.print(safeGet(i, j));
                System.out.print(" ");
            }
            System.out.println();
        }

        return this;
    }

    // prints with neighbor count
    //
    public ImmutableBoard debugPrint() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                int numNeighbors = countNeighbors(i, j);
                Character x = board.get(i*numCols + j);
                System.out.print(x + ":" + numNeighbors);
                System.out.print(" ");
            }
            System.out.println();
            System.out.println();
        }

        return this;
    }

    // numAlive - Useful for main to see if any cells are alive
    //
    public int numAlive() {
        int alive = 0;
        for (int i = 0; i < numRows * numCols; i++) {
            if (board.get(i) == LIVE_CHAR)
                alive++;
        }

        return alive;
    }

    // isSameBoard - detects if two board are the same
    public boolean isSameBoard(ImmutableBoard that) {
        for (int i = 0; i < numRows * numCols; i++) {
            if (board.get(i) != that.board.get(i))
                return false;
        }

        return true;
    }

    // clear - returns a new board, cleared
    //
    public ImmutableBoard clear() {
        return new ImmutableBoard(numRows, numCols);
    }

    // random - returns a new board, randomized
    public ImmutableBoard random() {
        return ImmutableBoard.random(numRows, numCols);
    }

    public ImmutableBoard generate() {
        ImmutableBoard newBoard = new ImmutableBoard(this);

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                int numNeighbors = countNeighbors(i, j);
                if (safeGet(i,j) == LIVE_CHAR) {
                    // dies
                    if (numNeighbors < 2 || numNeighbors > 3) {
                        newBoard.set(i, j, DEAD_CHAR);
                    }
                } else {
                    if (numNeighbors == 3) {
                        newBoard.set(i, j, LIVE_CHAR);
                    }
                }
            }
        }

        return newBoard;
    }

    // private

    private Character set(int row, int col, Character c) {
        if (isValidPosition(row, col))
            return board.set(row * numCols + col, c);
        else
            throw new RuntimeException("(" + row + "," + col + ") is invalid!");
    }

    private Character set(int index, Character c) {
        if (index < 0 || index >= numRows * numCols)
            throw new RuntimeException("index " + index + " is invalid!");
        return board.set(index, c);
    }


    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < numRows && col >= 0 && col < numCols;
    }

    private Character safeGet(int row, int col) {
        if (isValidPosition(row, col))
            return board.get(row * numCols + col);
        else
            return DEAD_CHAR;
    }

    private int countNeighbors(int row, int col) {
        int neighbors = 0;

        if (safeGet(row-1, col-1) == LIVE_CHAR)
            neighbors++;

        if (safeGet(row, col-1) == LIVE_CHAR)
            neighbors++;

        if (safeGet(row+1, col-1) == LIVE_CHAR)
            neighbors++;

        if (safeGet(row-1, col) == LIVE_CHAR)
            neighbors++;


        if (safeGet(row+1, col) == LIVE_CHAR)
            neighbors++;

        if (safeGet(row-1, col+1) == LIVE_CHAR)
            neighbors++;

        if (safeGet(row, col+1) == LIVE_CHAR)
            neighbors++;

        if (safeGet(row+1, col+1) == LIVE_CHAR)
            neighbors++;

        return neighbors;
    }
}
