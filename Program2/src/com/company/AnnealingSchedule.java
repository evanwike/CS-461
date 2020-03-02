package com.company;

import java.util.Random;

public class AnnealingSchedule {
    private double temp;
    private double coolingRate;
    private int attempts;
    private int successfulChanges;

    public AnnealingSchedule() {
        temp = 10000;
        coolingRate = .95;
        attempts = 0;
        successfulChanges = 0;
        run();
    }

    public void run() {
        Schedule current = new Schedule();

        while (temp > 1) {
            Schedule successor = new Schedule();

            int f1 = current.getFitness();
            int f2 = successor.getFitness();

            if (f2 > f1) {
                current = successor;
                successfulChanges++;
            } else {
                float prob = new Random().nextFloat();

                if (acceptanceProbability(f1, f2, this.temp) > prob)
                    current = successor;
            }

            if (successfulChanges >= 400 || attempts >= 4000) {
                if (attempts >= 4000 && successfulChanges == 0)
                    return;

                temp *= coolingRate;
                successfulChanges = 0;
                attempts = 0;

                System.out.println(String.format("TEMP: %f", temp));
                System.out.println(current);
            }
        }
    }

    public static double acceptanceProbability(int fitness, int newFitness, double temp){
        if(newFitness < fitness)
            return 1.0;
        return Math.exp((fitness - newFitness) / temp);
    }
}
