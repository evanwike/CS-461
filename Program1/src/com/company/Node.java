package com.company;

import java.util.Arrays;
import static com.company.Utils.deepCopy;
import static com.company.Puzzle.N;

public class Node {
    private Node parent;
    private int[][] state;
    private int level;          // # Moves so far
    private int row, col;       // Blank tile position
    private int manDist;        // Sum of Manhattan distances
    private int mpf;            // Manhattan Priority Function ( g(n) + h(n) )

    Node(Node parent, int[][] state, int level, int row, int col) {
        this.parent = parent;
        this.state = deepCopy(state, N);
        this.level = level;
        this.row = row;
        this.col = col;
        this.manDist = manhattan();
        this.mpf = manDist + level;     // g(n) + h(n)
    }

    public Node getParent() { return parent; }
    public int[][] getState() { return deepCopy(state, N); }
    public int getLevel() { return level; }
    public int getRow() { return row; }
    public int getCol() { return col; }
    public int getManDist() { return manDist; }
    public int getMPF() { return mpf; }

    // Sum of Manhattan distances of out of position blocks
    // Assumes goal state = ascending order, blank in bottom right position
    // Can update to check for different goal states
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
        for (int[] row : state)
            sb.append(Arrays.toString(row)).append('\n');
        return sb.toString();
    }
}
