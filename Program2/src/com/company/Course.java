package com.company;

public class Course {
    private String name;
    private int enrollment;
    private Instructor instructor;
    private Room room;
    private int time;

    public Course(String name, int enrollment) {
        this.name = name;
        this.enrollment = enrollment;
    }

    public String getName() {
        return name;
    }

    public int getEnrollment() {
        return enrollment;
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

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean hasValidInstructor() {
        return instructor.canTeach(this);
    }

    private String getFormattedTime() {
        if (time < 12) {
            return String.format("%d:00AM", time);
        } else if (time == 12) {
            return "12:00PM";
        } else {
            return String.format("%d:00PM", time - 12);
        }
    }

    public Course copy() {
        Course course = new Course(name, enrollment);
        course.setInstructor(instructor.copy());
        course.setRoom(room.copy());
        course.setTime(time);

        return course;
    }

    public String out() {
        return String.format("%s - %s (%s %s)", getFormattedTime(), room.out(), name, instructor.out());
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", enrollment=" + enrollment +
                ", instructor=" + instructor.out() +
                ", room=" + room.out() +
                ", time=" + time +
                '}';
    }


}
