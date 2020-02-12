//package com.company;
//
//import java.util.Iterator;
//import java.util.PriorityQueue;
//
//public class Solver {
//    private Move lastMove;
//
//    public Solver(Board initial) {
//        PriorityQueue<Move> moves = new PriorityQueue<>();
//        moves.add(new Move(initial));
//
//        PriorityQueue<Move> inversions = new PriorityQueue<>();
//        inversions.add(new Move(initial.invert()));
//
//        while (true) {
//            lastMove = expand(moves);
//            if (lastMove != null || expand(moves) != null)
//                return;
//        }
//    }
//
//    private Move expand(PriorityQueue<Move> moves) {
//        if (moves.isEmpty())
//            return null;
//
//        Move bestMove = removeMin(moves);
//    }
//
//    private Move removeMin(PriorityQueue<Move> moves) {
//        Iterator itr = moves.iterator();
//        int min = itr
//    }
//
//}
