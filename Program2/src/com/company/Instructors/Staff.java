package com.company.Instructors;

import com.company.Main;

public class Staff extends Instructor {
    public Staff(String name, String dept) {
        super(name, dept);
        coursesCanTeach = Main.COURSES.getStaffCourseList();
    }

    public Instructor getCopy() {
        return new Staff(this.getName(), this.getDept());
    }
}
