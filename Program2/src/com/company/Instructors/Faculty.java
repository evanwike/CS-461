package com.company.Instructors;

import com.company.Main;

public class Faculty extends Instructor {
    String[] courseNums;

    public Faculty(String name, String dept, String[] courseNums) {
        super(name, dept);
        this.courseNums = courseNums;
        coursesCanTeach = Main.COURSES.getFacultyCourseList(courseNums);
    }

    public String[] getCourseNums() {
        return courseNums.clone();
    }

    public Instructor getCopy() {
        return new Faculty(this.getName(), this.getDept(), this.getCourseNums());
    }
}
