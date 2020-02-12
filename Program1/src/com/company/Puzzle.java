package com.company;

import java.util.*;
import java.io.PrintWriter;

// Utility methods
import static com.company.Utils.*;

public class Puzzle {
    private static final int N = 3;
    private static final int[][] GOAL = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    private int[][] initial;
    private PrintWriter pw;
    private Set<String> visited;


    Puzzle(int[][] initial, PrintWriter pw) {
        this.initial = initial.clone();
        this.pw = pw;
        this.visited = new HashSet<>();
    }

    // Total number of tiles out of position
    public int hamming() {
        int count = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int tile = initial[i][j];
                int goal = GOAL[i][j];

                if (tile != 0 && tile != goal)
                    count++;
            }
        }
        return count;
    }

    // Determines if puzzle is solvable by counting the number of inversions
    public boolean isSolvable() {
        int inversions = 0;
        ArrayList<Integer> flatten = flatten(initial);
        Integer[] arr = flatten.toArray(new Integer[0]);

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i+1; j < arr.length; j++) {
                int first = arr[i];
                int second = arr[j];

                // When i < j, but i appears after j
                if (first != 0 && second != 0 && first > second)
                    inversions++;
            }
        }

        // N = ODD -> ODD # of inversions = unsolvable  -> false
        // N = ODD -> EVEN # of inversions = solvable   -> true
        return inversions % 2 == 0;
    }
    
    public void solve() {
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.getMPF() - b.getMPF());
        int[] bS = findBlankSpace();
        Node root = new Node(null, initial, 0, bS[0], bS[1]);

        pq.add(root);

        while (!pq.isEmpty()) {
            Node min = pq.poll();

            // Goal state
            if (min.getMPF() == 0) {
                printPath(min);
                return;
            }

            Iterable<Node> neighbors = getNeighbors(min);
            Iterator<Node> itr = neighbors.iterator();

            while (itr.hasNext())
                pq.add(itr.next());

            pq.remove(min);
        }
    }

    // Find all neighboring Nodes, only add if not previously visited
    private Iterable<Node> getNeighbors(Node node) {
        LinkedList<Node> neighbors = new LinkedList<>();
        int row = node.getRow();
        int col = node.getCol();

        // Move up
        if (row > 0) {
            Node up = getTopNeighbor(node, row, col);
            if (notVisited(up))
                neighbors.add(up);
        }

        // Move down
        if (row < N - 1) {
            Node down = getBottomNeighbor(node, row, col);
            if (notVisited(down))
                neighbors.add(down);
        }

        // Move left
        if (col > 0) {
            Node left = getLeftNeighbor(node, row, col);
            if (notVisited(left))
                neighbors.add(left);
        }

        // Move right
        if (col < N - 1) {
            Node right = getRightNeighbor(node, row, col);
            if (notVisited(right))
                neighbors.add(right);
        }

        return neighbors;
    }

    private Node getTopNeighbor(Node node, int row, int col) {
        int newRow = row - 1;

        int[][] newState = swap(node.getState(), row, col, newRow, col);
        return new Node(node, newState, node.getLevel() + 1, newRow, col);
    }

    private Node getBottomNeighbor(Node node, int row, int col) {
        int newRow = row + 1;
        int[][] newState = swap(node.getState(), row, col, newRow, col);

        return new Node(node, newState, node.getLevel() + 1, newRow, col);
    }

    private Node getLeftNeighbor(Node node, int row, int col) {
        int newCol = col - 1;
        int[][] newState = swap(node.getState(), row, col, row, newCol);

        return new Node(node, newState, node.getLevel() + 1, row, newCol);
    }

    private Node getRightNeighbor(Node node, int row, int col) {
        int newCol = col + 1;
        int[][] newState = swap(node.getState(), row, col, row, newCol);

        return new Node(node, newState, node.getLevel() + 1, row, newCol);
    }

    // Swap empty tile in array representation of initial
    private int[][] swap(int[][] state, int row, int col, int row1, int col1) {
        int[][] copy = deepCopy(state, N);
        int temp = copy[row][col];

        copy[row][col] = copy[row1][col1];
        copy[row1][col1] = temp;

        return copy;
    }

    // Used to find position of initial blank space
    private int[] findBlankSpace() {
        int[] coords = new int[2];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (initial[i][j] == 0) {
                    coords[0] = i;
                    coords[1] = j;

                    break;
                }
            }
        }
        return coords;
    }

    // Checks to see if current node has already been visited, marks as visited if not
    private boolean notVisited(Node node) {
        String nodeStr = node.toString();

        if (visited.contains(nodeStr))
            return false;
        else
            visited.add(nodeStr);
        return true;
    }

    private void printPath(Node root) {
        if (root == null) return;

        printPath(root.getParent());
        pw.write(root.toString());
        pw.flush();
    }

    // Used for testing initial state
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int[] row : initial) {
            for (int col : row)
                sb.append(String.format("%d ", col));
            sb.append('\n');
        }

        return sb.toString();
    }
}
