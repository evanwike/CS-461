package com.company;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class SimulatedAnnealing {
    private double T = 10000;
    private static final double coolingRate = .95;
    private int attempts = 0;
    private int successfulChanges = 0;
    private int total = 0;
    private Schedule bestSchedule;
    private int maxFitness = 0;

    public SimulatedAnnealing() {
        run();
    }

    public void run() {
        Schedule current = new Schedule();

        // FIXME: Should be while true, but results in infinite loop
        while (T > .00000001) {
            Schedule successor = new Schedule(current);

            int f1 = current.getFitness();
            int f2 = successor.getFitness();

           // Track best schedule
            if (f2 > maxFitness) {
                bestSchedule = successor;
                maxFitness = f2;
            }

            // If successor > current or accepted by probability
            if (accept(f1, f2)) {
                current = successor;
                successfulChanges++;
            }

            // Lower temperature after 400 successful changes OR 4000 attempts
            if (successfulChanges >= 400 || attempts >= 4000) {
                // Stop after 4000 attempts without a successful change
                if (attempts >= 4000 && successfulChanges == 0) {
                    break;
                }

                T *= coolingRate;
                successfulChanges = 0;
                attempts = 0;

                System.out.println(String.format("TEMP: %f", T));
                System.out.println(current);
            }
            attempts++;
            total++;
        }

        System.out.println(bestSchedule);
        out(current);
    }

    // Acceptance function
    private boolean accept(int f1, int f2) {
        double P = Math.random();

        // If new state is better, accept
        if (f2 > f1)
            return 1.0 > P;

        // Else, take with probability
        return Math.exp((f1 - f2) / T) > P;
    }

    private void out(Schedule _final) {
        PrintWriter pw;

        try {
            pw = new PrintWriter("solution.txt");
            pw.write(String.format("ITERATIONS: %d\n\n", total));

            pw.write("FINAL Schedule:\n");
            pw.write(_final.toString());
            pw.write("\n");
            pw.write("BEST Schedule:\n");
            pw.write(bestSchedule.toString());
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
