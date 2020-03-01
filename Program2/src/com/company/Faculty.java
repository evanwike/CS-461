package com.company;

// Can only teach specified courses
public class Faculty extends Instructor {
    private String[] courseNums;

    public Faculty(String name, String dept, String[] courseNums) {
        super(name, dept);
        this.courseNums = courseNums;
    }

    public void initCourses() {
        // courseNum 101 would add 101A, 101B, etc...
        for (String courseNum : courseNums) {
            Main.COURSES.forEach(course -> {
                String name = course.getCourseNum();

                if (name.contains(courseNum))
                    coursesCanTeach.add(course);
            });
        }
    }
}
