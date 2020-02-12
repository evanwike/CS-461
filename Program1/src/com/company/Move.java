package com.company;

public class Move implements Comparable<Move> {
    private Move prev;
    private Board board;
    private int moves;

    public Move(Board board) {
        this.board = board;
    }

    public Move(Board board, Move prev) {
        this.board = board;
        this.prev = prev;
        this.moves = prev.moves + 1;
    }

    @Override
    public int compareTo(Move move) {
        return this.board.manhattan() - move.board.manhattan() + (this.moves - move.moves);
    }
}
