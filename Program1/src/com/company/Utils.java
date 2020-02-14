package com.company;

import java.util.ArrayList;

abstract class Utils {
    public static int[][] deepCopy(int[][] state, int N) {
        int[][] copy = new int[N][N];

        for (int i = 0; i < N; i++)
            System.arraycopy(state[i], 0, copy[i], 0, N);

        return copy;
    }

    public static Integer[] flatten(int[][] board) {
        ArrayList<Integer> flattened = new ArrayList<>();

        for (int[] row : board)
            for (int col : row)
                flattened.add(col);

        return flattened.toArray(new Integer[0]);
    }
}
