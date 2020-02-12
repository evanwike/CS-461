package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner;
        ArrayList<Board> boards = new ArrayList<>();

        try {
            scanner = new Scanner(new File("data.txt"));

            while (scanner.hasNext()) {
                int[][] board = new int[3][3];

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        int n = scanner.nextInt();
                        board[i][j] = n;
                    }
                }
                boards.add(new Board(board));
            }

        }
        catch (FileNotFoundException fnf) {
            System.out.println("Could not find file.");
        }

        printBoards(boards);
    }

    public static void printBoards(ArrayList<Board> boards) {
        for (Board board : boards) {
            System.out.println(board.toString());
        }
    }
}
