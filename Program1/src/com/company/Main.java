package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner;
        PrintWriter writer;
        ArrayList<Puzzle> puzzles = new ArrayList<>();

        try {
            scanner = new Scanner(new FileInputStream("data.txt"));
            writer = new PrintWriter("solutions.txt");

            while (scanner.hasNext()) {
                Puzzle puzzle = new Puzzle(getBoardFromInput(scanner), writer);
                puzzles.add(puzzle);
            }

            scanner.close();
            solvePuzzles(puzzles, writer);
        }
        catch (FileNotFoundException fnf) {
            System.out.println("An error occurred while attempting to open the input file.");
        }

    }

    private static void solvePuzzles(ArrayList<Puzzle> puzzles, PrintWriter writer) {
        int i = 1;

        for (Puzzle puzzle : puzzles) {
            writer.write(String.format("PUZZLE %d\n", i));

            if (puzzle.isSolvable()) {
                puzzle.solve();
            } else {
                writer.write(puzzle.getInitial());
                writer.write("No solution for initial state exists.\n");
                writer.flush();
            }
            i++;
            writer.write('\n');
        }
        writer.close();
    }

    private static int[][] getBoardFromInput(Scanner scanner) {
        int[][] board = new int[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = scanner.nextInt();
            }
        }
        return board;
    }

    // Used for testing board input
    private static void printBoards(ArrayList<Puzzle> puzzles) {
        for (Puzzle puzzle : puzzles) {
            System.out.println(puzzle);
        }
    }
}
