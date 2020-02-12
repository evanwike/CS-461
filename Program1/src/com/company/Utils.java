package com.company;

import java.util.ArrayList;

public class Utils {

    private Utils() {}

    // Deep copy array of arrays, as .clone() only provides a shallow copy, and arrays are objects
    public static int[][] deepCopy(int[][] state, int N) {
        int[][] copy = new int[N][N];

        for (int i = 0; i < N; i++) {
            int[] temp = new int[N];

            for (int j = 0; j < N; j++)
                temp[j] = state[i][j];

            copy[i] = temp;
        }
        return copy;
    }

    // Flatten 2D array representation of board
    public static ArrayList<Integer> flatten(int[][] board) {
        ArrayList<Integer> flattened = new ArrayList<>();

        for (int[] row : board)
            for (int col : row)
                flattened.add(col);

        return flattened;
    }
}
