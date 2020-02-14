package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static PrintWriter pw;
    private static Scanner scanner;

    public static void main(String[] args) {
        ArrayList<Puzzle> puzzles = new ArrayList<>();

        try {
            scanner = new Scanner(new FileInputStream("data.txt"));
            pw = new PrintWriter("solutions.txt");

            while (scanner.hasNext()) {
                Puzzle puzzle = new Puzzle(getBoardFromInput());
                puzzles.add(puzzle);
            }

            scanner.close();
            solvePuzzles(puzzles);
        }
        catch (FileNotFoundException fnf) {
            System.out.println("An error occurred while attempting to open the input file.");
        }

    }
    
    // Calls the solve method on each puzzle
    private static void solvePuzzles(ArrayList<Puzzle> puzzles) {
        int i = 1;

        for (Puzzle puzzle : puzzles) {
            pw.write(String.format("PUZZLE %d\n", i));

            puzzle.solve();
            i++;
            pw.write('\n');
        }
        pw.close();
    }

    // Processes each array from input file
    private static int[][] getBoardFromInput() {
        int[][] board = new int[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = scanner.nextInt();
            }
        }
        return board;
    }
}
