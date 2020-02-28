package com.company;

public class Course {
    private String dept;
    private String name;
    private int enrollment;

    public Course(String dept, String name, int enrollment) {
        this.dept = dept;
        this.name = name;
        this.enrollment = enrollment;
    }

    public String getDept() {
        return dept;
    }

    public String getName() {
        return name;
    }

    public int getEnrollment() {
        return enrollment;
    }

    @Override
    public String toString() {
        return "Course{" +
                "dept='" + dept + '\'' +
                ", name='" + name + '\'' +
                ", enrollment=" + enrollment +
                '}';
    }
}
