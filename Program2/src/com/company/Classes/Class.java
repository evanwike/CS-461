package com.company.Classes;

import com.company.Courses.Course;
import com.company.Instructors.Instructor;
import com.company.Rooms.Room;

public class Class {
    private Course course;
    private Instructor instructor;
    private Room room;
    private int time;

    public Class(Course course, Instructor instructor, Room room) {
        this.course = course;
        this.instructor = instructor;
        this.room = room;
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

    public void setTime(int time) {
        this.time = time;
    }

    public String getFormattedTime() {
        if (time < 12) {
            return String.format("%d:00AM", time);
        } else if (time == 12) {
            return "12:00PM";
        } else {
            return String.format("%d:00PM", time - 12);
        }
    }

    @Override
    public String toString() {
        return String.format(
                "%s: %s %s (%s-%s %s)",
                getFormattedTime(),
                room.getBuilding(), room.getRoomNum(),
                course.getDept(), course.getCourseNum(),
                instructor.getName());
    }
}
