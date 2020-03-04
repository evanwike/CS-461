package com.company;

import java.util.List;

public class Instructor {
    private String name;
    private String dept;
    private List<Course> courses;

    public Instructor(String name, String dept, List<Course> courses) {
        this.name = name;
        this.dept = dept;
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public String getDept() {
        return dept;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public Instructor copy() {
        return new Instructor(name, dept, courses);
    }

    public boolean isFaculty() {
        return !name.equals("Staff");
    }

    public boolean canTeach(Course course) {
        return courses.stream().anyMatch(c -> c.getName().equals(course.getName()));
    }

    public String out() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append('\n');

        for (Course c : courses)
            sb.append(c).append('\n');
        return sb.toString();
    }
}
