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

    public List<Course> getStaffCourseList() {
        return new ArrayList<>(courses);
    }

    public List<Course> getFacultyCourseList(String[] courseNums) {
        List<Course> list = new ArrayList<>();

        for (String num : courseNums) {
            courses.forEach(course -> {
                String courseNum = course.getCourseNum();
                if (courseNum.contains(num))
                    list.add(course);
            });
        }

        return list;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "courses=" + courses +
                '}';
    }
}
