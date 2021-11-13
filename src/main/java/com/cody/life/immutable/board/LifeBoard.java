package com.cody.life.immutable.board;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class LifeBoard {

    static Character DEAD_CHAR = '○';
    static Character LIVE_CHAR = '⬤';

    interface PositionGenerator {
        Character get();
    }

    private final Character[] board;
    private final int numRows;
    private final int numCols;

    private long timingNanos;

    private static final Random random = new Random();

    // Standard (rows, cols) constructor
    //
    private LifeBoard(int rows, int cols, PositionGenerator posGen) {
        this.timingNanos = System.nanoTime();

        this.numRows = rows;
        this.numCols = cols;

        board = _allocate(numRows, numCols, posGen);
    }

    //
    // Statics -=-=-=-=-=-=-=-=-=-=-=-=-=-===-=-=-=-=
    //

    public static LifeBoard random(int rows, int cols) {
        return random(rows, cols,random);
    }

    public static LifeBoard random(int rows, int cols, Random r) {
        return new LifeBoard(rows, cols, () -> {
        if (r.nextBoolean())
            return LIVE_CHAR;
        else
            return DEAD_CHAR;
        });
    }

    private static Character[] _allocate(int rows, int cols, PositionGenerator posGen) {
        Character[] _board = new Character[rows * cols];

        for (int i = 0; i < rows * cols; i++) {
            _board[i] = posGen.get();
        }
        return _board;
    }

    public static void writeToFile(LifeBoard board, String filename) throws IOException {
        File file = new File(filename);
        FileOutputStream fos = new FileOutputStream(file);
        write(board, fos);
    }

    public static void write(LifeBoard board, OutputStream out) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));
        bufferedWriter.write(board.numRows);
        bufferedWriter.newLine();
        bufferedWriter.write(board.numCols);
        bufferedWriter.newLine();
        for (int i = 0; i < board.numRows; i++) {
            for (int j = 0; j < board.numCols; j++) {
                bufferedWriter.write(board.safeGet(i, j));
            }
            bufferedWriter.newLine();
        }

        bufferedWriter.close();
    }

    public static LifeBoard readFromFile(LifeBoard board, String filename) throws IOException {
        File file = new File(filename);
        FileInputStream fileInputStream = new FileInputStream(file);
        return read(fileInputStream);
    }

    public static LifeBoard read(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        int numRows = Integer.parseInt(bufferedReader.readLine());
        int numCols = Integer.parseInt(bufferedReader.readLine());
        String line = bufferedReader.readLine();

        LifeBoard board = new LifeBoard(numRows, numCols, new PositionGenerator() {
            int index = 0;
            @Override
            public Character get() {
                return line.charAt(index++);
            }
        });

        bufferedReader.close();

        return board;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getBufferLength() {
        return board.length;
    }

    // print
    //
    public LifeBoard print() {
        Charset utf8 = StandardCharsets.UTF_8;
        PrintWriter printWriter = new PrintWriter(System.out,true, utf8);

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                printWriter.print(safeGet(i, j));
                printWriter.print(" ");
            }
            printWriter.println();
        }

        return this;
    }

    public LifeBoard debugPrint() {
        Charset utf8 = StandardCharsets.UTF_8;
        PrintWriter printWriter = new PrintWriter(System.out,true, utf8);
        Character[] liveNeighbors = {'⓿', '➊', '➋', '➌', '➍', '➎', '➏', '➐', '➑' };
        Character[] deadNeighbors = {'⓪', '➀', '➁', '➂', '➃', '➄', '➅', '➆', '➇' };

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                Character c;
                if (safeGet(i, j) == LIVE_CHAR) {
                    c = liveNeighbors[countNeighbors(i,j)];
                } else {
                    c = deadNeighbors[countNeighbors(i,j)];
                }
                printWriter.print(c);
                printWriter.print(" ");
            }
            printWriter.println();
        }

        return this;
    }

    public LifeBoard separator() {
        for (int j=0; j < numCols; j++) {
            System.out.print("--");
        }
        System.out.println();
        return this;
    }

    public LifeBoard recordTiming() {
        timingNanos = System.nanoTime();
        return this;
    }

    public LifeBoard printTiming(String message) {
        long now = System.nanoTime();
        System.out.println(message + ": " + (now-timingNanos) / 1000 + " micros");
        timingNanos = now;
        return this;
    }

    // numAlive - Useful for main to see if any cells are alive
    //
    public int numAlive() {
        int alive = 0;
        for (int i = 0; i < numRows * numCols; i++) {
            if (board[i] == LIVE_CHAR)
                alive++;
        }

        return alive;
    }

    // isSameBoard - detects if two board are the same
    public boolean isSameBoard(LifeBoard that) {
        for (int i = 0; i < numRows * numCols; i++) {
            if (board[i] != that.board[i])
                return false;
        }

        return true;
    }


    public LifeBoard generate() {
        return new LifeBoard(numRows, numCols, new PositionGenerator() {
            int index = 0;

            @Override
            public Character get() {
                int i = index / numCols;
                int j = index % numCols;
                index++;

                int numNeighbors = countNeighbors(i, j);

                if (safeGet(i,j) == LIVE_CHAR) {
                    if (numNeighbors < 2 || numNeighbors > 3) {
                        return DEAD_CHAR;
                    } else {
                        return LIVE_CHAR;
                    }
                } else {
                    if (numNeighbors == 3) {
                        return LIVE_CHAR;
                    } else {
                        return DEAD_CHAR;
                    }
                }
            }
        });
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < numRows && col >= 0 && col < numCols;
    }

    private Character safeGet(int row, int col) {
        if (isValidPosition(row, col))
            return board[row * numCols + col];
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
