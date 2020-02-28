package com.company;

import java.util.Arrays;
import java.util.List;

public class Adjunct extends Instructor {
    private List<String> coursesCanTeach;

    public Adjunct(String name, String department) {
        super(name, department);
    }

    public List<String> getCoursesCanTeach() {
        return coursesCanTeach;
    }

    public void setCoursesCanTeach(String[] coursesCanTeach) {
        this.coursesCanTeach = Arrays.asList(coursesCanTeach);
    }

    @Override
    public String toString() {
        return "Adjunct{" +
                "coursesCanTeach=" + coursesCanTeach +
                '}';
    }
}
