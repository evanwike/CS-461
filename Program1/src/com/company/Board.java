package com.company;

import java.util.LinkedList;

public class Board {
    private static final int N = 3;
    private int[][] tiles;
    private int[][] goal = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
    };

    public Board(int[][] tiles) {
        this.tiles = tiles.clone();
    }

    // String representation of board
    public String toString() {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                str.append(String.format("%d ", tiles[i][j]));
            }
            str.append("\n");
        }
        return str.toString();
    }

    // Return tile at position
    public int tileAt(int row, int col) {
        return tiles[row][col];
    }

    // Number of tiles out of place
    public int hamming() {
        int count = 0;

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                int tile = tiles[i][j];
                int goal = i * 3 + j + 1;

                if (tile != 0 && tile != goal)
                    count++;
            }
        }
        return count;
    }

    // Sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int tile = tiles[i][j];

                int row = Math.abs(i - ((tile - 1) / 3));
                int col = Math.abs(j - ((tile - 1) % 3));

                sum += tile == 0 ? 0 : row + col;
            }
        }
        return sum;
    }

    // Check if in goal state
    public boolean isGoal() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != goal[i][j])
                    return false;
            }
        }
        return true;
    }

    // Check if two boards are equivalent
    public boolean equals(Board b) {
        if (b == this)
            return true;
        else if (b == null || b.tiles.length != tiles.length)
            return false;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != b.tiles[i][j])
                    return false;
            }
        }
        return true;
    }

    // Find all neighboring boards
    public Iterable<Board> neighbors() {
        LinkedList<Board> neighbors = new LinkedList<>();
        int[] emptyPosition = findEmptyPosition();
        int eX = emptyPosition[0];
        int eY = emptyPosition[1];

        // Move up
        if (eX > 0)
            neighbors.add(moveUp(eX, eY));

        // Move down
        if (eX < N - 1)
            neighbors.add(moveDown(eX, eY));

        // Move left
        if (eY > 0)
            neighbors.add(moveLeft(eX, eY));

        // Move right
        if (eY < N - 1)
            neighbors.add(moveRight(eX, eY));

        return neighbors;
    }

    public boolean isSolvable() {
        return true;
    }

    // Determine if board has inversion and can't be solved
    public Board invert() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != 0 && tiles[i][j+1] != 0) {
                    int[][] b = swap(i, j, i, j + 1);
                    return new Board(b);
                }
            }
        }
        throw new RuntimeException();
    }

    // Return position of empty tile
    public int[] findEmptyPosition() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int tile = tiles[i][j];

                if (tile == 0) {
                    return new int[]{i, j};
                }
            }
        }
        throw new RuntimeException();
    }

    // Swap empty tile
    private int[][] swap(int x1, int y1, int x2, int y2) {
        int[][] copy = tiles.clone();
        int temp = copy[x1][y1];

        copy[x1][y1] = copy[x2][y2];
        copy[x2][y2] = temp;

        return copy;
    }

    // Move empty tile up
    public Board moveUp(int x, int y) {
        int[][] b = swap(x, y, x - 1, y);
        return new Board(b);
    }

    // Move empty tile down
    public Board moveDown(int x, int y) {
        int[][] b = swap(x, y, x + 1, y);
        return new Board(b);
    }

    // Move empty tile left
    public Board moveLeft(int x, int y) {
        int[][] b = swap(x, y, x, y - 1);
        return new Board(b);
    }

    // Move empty tile right
    public Board moveRight(int x, int y) {
        int[][] b = swap(x, y, x, y + 1);
        return new Board(b);
    }
}
