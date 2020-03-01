package com.company;

import com.company.Courses.Course;
import com.company.Instructors.Instructor;
import com.company.Rooms.Room;

public class Class {
    private Course course;
    private Instructor instructor;
    private Room room;
    private int time;

    public Class(Course course, Instructor instructor, Room room, int time) {
        this.course = course;
        this.instructor = instructor;
        this.room = room;
        this.time = time;
    }

    public Course getCourse() {
        return course;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public Room getRoom() {
        return room;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Class{" +
                "course=" + course +
                ", instructor=" + instructor +
                ", room=" + room +
                ", time=" + time +
                '}';
    }
}
