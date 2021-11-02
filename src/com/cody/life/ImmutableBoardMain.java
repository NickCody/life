package com.cody.life;

public class ImmutableBoardMain {

    public static void main(String[] args) {

        // initial board
        //
        int rows = 15;
        int cols = 15;

        ImmutableBoard initialBoard = ImmutableBoard.create(rows, cols).random();
        ImmutableBoard board = initialBoard;

        // statistics used to continue or break
        //
        int numAlive = board.numAlive();
        int generation = 1;
        final int MAX_GENERATIONS = rows * cols;
        boolean same = false;

        while (numAlive > 0 && !same && generation <= MAX_GENERATIONS) {

            //clearConsole();
            // print our current generation
            //
            System.out.println("Generation " + generation++);
            board.print();

            wait(100);
            // Generate new generation and update stats
            //
            ImmutableBoard newGeneration = board.generate();
            same = board.isSameBoard(newGeneration);
            board = newGeneration;
            numAlive = board.numAlive();
        }

        if (same) {
            System.out.println("Detected identical generation at generation " + generation + "!");
            board.print();
        } else if (numAlive == 0) {
            System.out.println("Detected extinction at generation " + generation + "!");
            board.print();
        } else if (generation > MAX_GENERATIONS) {
            System.out.println("Wow! The following initial board lasted a long time!");
            initialBoard.print();
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

    public static void clearConsole()
    {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }
}
