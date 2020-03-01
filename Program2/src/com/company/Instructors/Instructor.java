package com.company.Instructors;

import com.company.Courses.Course;

import java.util.List;

public abstract class Instructor {
    private String name;
    private String dept;
    List<Course> coursesCanTeach;

    public Instructor(String name, String dept) {
        this.name = name;
        this.dept = dept;
    }

    public String getName() {
        return name;
    }

    public String getDept() {
        return dept;
    }

    public List<Course> getCoursesCanTeach() {
        return coursesCanTeach;
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
