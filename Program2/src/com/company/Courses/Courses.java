package com.company.Courses;

import com.company.Main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Courses {
    private List<Course> courses;

    public Courses() {
        courses = new ArrayList<>();

        try {
            processCourseData();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void processCourseData() throws IOException {
        Scanner scanner = new Scanner(new FileInputStream(Main.PATH + Main.COURSE_DATA));

        while (scanner.hasNextLine()) {
            String dept = scanner.next();
            String courseNum = scanner.next();
            int expEnrollment = scanner.nextInt();

            courses.add(new Course(dept, courseNum, expEnrollment));
        }
    }

    public List<String> getStaffCourseList() {
        List<String> courseList = new ArrayList<>();
        courses.forEach(course -> courseList.add(course.getCourseNum()));
        return courseList;
    }

    public List<String> getFacultyCourseList(String[] courseNums) {
        List<String> courseList = new ArrayList<>();

        for (String num : courseNums) {
            courses.forEach(course -> {
                String courseNum = course.getCourseNum();

                if (courseNum.contains(num))
                    courseList.add(courseNum);
            });
        }

        return courseList;
    }

    public List<Course> getCourses() {
        List<Course> copy = new ArrayList<>();
        courses.forEach(course -> copy.add(course.getCopy()));
        return copy;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "courses=" + courses +
                '}';
    }
}
