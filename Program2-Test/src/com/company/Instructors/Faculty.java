package com.company.Instructors;

import com.company.Main;

public class Faculty extends Instructor {

    public Faculty(String name, String dept, String[] courseNums) {
        super(name, dept);
        coursesCanTeach = Main.COURSES.getFacultyCourseList(courseNums);
    }
}
