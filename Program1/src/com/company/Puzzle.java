package com.company;

import java.util.*;
import static com.company.Main.pw;
import static com.company.Utils.*;

public class Puzzle {
    public static final int N = 3;      // Dimension of board (N * N)
    private int[][] initial;
    private Set<Integer> visited;

    Puzzle(int[][] initial) {
        this.initial = deepCopy(initial, N);
        this.visited = new HashSet<>();
    }

    // Determines if puzzle is solvable by counting the number of inversions
    public boolean isSolvable() {
        int inversions = 0;
        Integer[] arr = flatten(initial);

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                int a = arr[i];
                int b = arr[j];

                // When i < j, but i appears after j
                if (a != 0 && b != 0 && a > b)
                    inversions++;
            }
        }
        // N = ODD -> ODD # of inversions = unsolvable  -> false
        // N = ODD -> EVEN # of inversions = solvable   -> true
        return inversions % 2 == 0;
    }
    
    public void solve() {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(Node::getMPF));
        int[] bP = getBlankPosition();
        Node root = new Node(null, initial, 0, bP[0], bP[1]);

        pq.add(root);

        if (!isSolvable()) {
            printPath(root);
            pw.write("No solution exists.\n");
            return;
        }

        while (!pq.isEmpty()) {
            Node min = pq.poll();

            // If sum of Manhattan distances == 0, no blocks are out of position - thus, in goal state
            if (min.getManDist() == 0) {
                printPath(min);
                return;
            }

            for (Node neighbor : getNeighbors(min))
                pq.add(neighbor);

            pq.remove(min);
        }
    }

    // Find all neighboring Nodes, only add if not previously visited
    private Iterable<Node> getNeighbors(Node node) {
        LinkedList<Node> neighbors = new LinkedList<>();
        int row = node.getRow();
        int col = node.getCol();
        int[][] newState;

        // Move up
        if (row > 0) {
            newState = swap(node.getState(), row, col, row - 1, col);
            if (notVisited(newState))
                neighbors.add(new Node(node, newState, node.getLevel() + 1, row - 1, col));
        }
        // Move down
        if (row < N - 1) {
            newState = swap(node.getState(), row, col, row + 1, col);
            if (notVisited(newState))
                neighbors.add(new Node(node, newState, node.getLevel() + 1, row + 1, col));
        }
        // Move left
        if (col > 0) {
            newState = swap(node.getState(), row, col, row, col - 1);
            if (notVisited(newState))
                neighbors.add(new Node(node, newState, node.getLevel() + 1, row, col - 1));
        }
        // Move right
        if (col < N - 1) {
            newState = swap(node.getState(), row, col, row, col + 1);
            if (notVisited(newState))
                neighbors.add(new Node(node, newState, node.getLevel() + 1, row, col + 1));
        }
        return neighbors;
    }

    // Swap blank tile with tile at position [newRow, newCol]
    private int[][] swap(int[][] state, int row, int col, int newRow, int newCol) {
        int[][] copy = deepCopy(state, N);
        int temp = copy[row][col];

        copy[row][col] = copy[newRow][newCol];
        copy[newRow][newCol] = temp;

        return copy;
    }

    // Used to find position of initial blank space
    private int[] getBlankPosition() {
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

    // Checks to see if neighbor state has already been visited
    private boolean notVisited(int[][] state) {
        // Used to track visited states
        int hash = Arrays.hashCode(flatten(state));

        if (visited.contains(hash)) return false;
        else visited.add(hash);

        return true;
    }

    // Moves from initial state -> goal state
    private Stack<Node> getPath(Node leaf) {
        Stack<Node> path = new Stack<>();
        Node parent = leaf.getParent();
        path.push(leaf);

        while (parent != null) {
            path.push(parent);
            parent = parent.getParent();
        }
        return path;
    }

    private void printPath(Node node) {
        Stack<Node> path = getPath(node);

        while (!path.empty()) {
            pw.write(path.pop().toString());

            if (path.size() > 0)
                pw.write("    \u2193\n");
        }
    }
}
