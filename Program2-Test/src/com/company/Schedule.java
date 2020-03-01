package com.company;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    List<Class> classes;
    private double fitness;
    private int conflicts;

    public Schedule() {
        this.classes = new ArrayList<>();
        this.fitness = 0.0;
        this.conflicts = 0;
    }
}
