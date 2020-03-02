package com.company.Courses;

public class Course {
    private String dept;
    private String courseNum;
    private int expEnrollment;

    public Course(String dept, String courseNum, int expEnrollment) {
        this.dept = dept;
        this.courseNum = courseNum;
        this.expEnrollment = expEnrollment;
    }

    public String getDept() {
        return dept;
    }

    public String getCourseNum() {
        return courseNum;
    }

    public int getExpEnrollment() {
        return expEnrollment;
    }

    public Course getCopy() { return new Course(this.dept, this.courseNum, this.expEnrollment); }

    @Override
    public String toString() {
        return "Course{" +
                "dept='" + dept + '\'' +
                ", courseNum='" + courseNum + '\'' +
                ", expEnrollment=" + expEnrollment +
                '}';
    }
}
