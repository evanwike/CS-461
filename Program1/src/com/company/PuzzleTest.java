package com.company;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class PuzzleTest {
    private static final int[][] a1 = {{1, 2, 3}, {0, 4, 6}, {8, 5, 7}};
    private static final int[][] a2 = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
    private Puzzle p1, p2;

    PuzzleTest() throws IOException {
        p1 = new Puzzle(a1);
        p2 = new Puzzle(a2);
    }

    @Test
    void isSolvable() {
        assertFalse(p1.isSolvable());
        assertTrue(p2.isSolvable());
    }
}