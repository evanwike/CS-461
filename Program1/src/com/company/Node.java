package com.company;

import java.util.ArrayList;

import static com.company.Utils.deepCopy;
import static com.company.Utils.flatten;

public class Node {
    private static final int N = 3;
    private Node parent;
    private int[][] state;
    private int level;          // # Moves so far
    private int row, col;       // Blank tile position
    private int mpf;            // Manhattan Priority Function

    Node(Node parent, int[][] state, int level, int row, int col) {
        this.parent = parent;
        this.state = deepCopy(state, N);
        this.level = level;
        this.row = row;
        this.col = col;
        this.mpf = manhattan() + level;
    }

    public Node getParent() { return parent; }
    public int[][] getState() { return state.clone(); }
    public int getLevel() { return level; }

    // Get coordinates of blank space
    public int getRow() { return row; }
    public int getCol() { return col; }

    public int getMPF() { return mpf; }

    private int manhattan() {
        int sum = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int tile = state[i][j];

                int row = Math.abs(i - ((tile - 1) / 3));
                int col = Math.abs(j - ((tile - 1) % 3));

                sum += tile == 0 ? 0 : row + col;
            }
        }
        return sum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int[] row : state) {
            for (int col : row)
                sb.append(String.format("%d ", col));
            sb.append('\n');
        }

        return sb.toString();
    }
}
