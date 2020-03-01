package com.company;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class Courses {
    private Map<String, Course> courses;

    public Courses() {
        courses = new HashMap<>();
    }

    public void addCourse(String dept, String courseNum, int enrollment) {
        courses.put(courseNum, new Course(dept, courseNum, enrollment));
    }

    public HashSet<Course> getCourseSections(String courseNum) {
        Set<Course> sections = new HashSet<>();
        return courses.entrySet().forEach();
    }

    @Override
    public String toString() {
        return "Courses{" +
                "courses=" + courses +
                '}';
    }
}
