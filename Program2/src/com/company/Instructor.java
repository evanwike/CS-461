package com.company;

import java.util.ArrayList;
import java.util.List;

public abstract class Instructor {
    private String name;
    private String dept;
    List<Course> coursesCanTeach;

    public Instructor(String name, String dept) {
        this.name = name;
        this.dept = dept;
        coursesCanTeach = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDept() {
        return dept;
    }

    // Overridden by Faculty, used by Staff
    public void initCourses() {
        coursesCanTeach.addAll(Main.COURSES);
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "name='" + name + '\'' +
                ", dept='" + dept + '\'' +
                ", coursesCanTeach=" + coursesCanTeach +
                '}';
    }
}
