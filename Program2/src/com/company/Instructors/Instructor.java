package com.company.Instructors;

import com.company.Courses.Course;

import java.util.List;

public abstract class Instructor {
    private String name;
    private String dept;
    List<String> coursesCanTeach;

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

    public List<String> getCoursesCanTeach() {
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

    public boolean canTeach(String courseNum) {
        return coursesCanTeach.contains(courseNum);
    }

    public abstract Instructor getCopy();

    public boolean isFaculty() {
        return this instanceof Faculty;
    }

    public boolean isStaff() {
        return this instanceof Staff;
    }
}
