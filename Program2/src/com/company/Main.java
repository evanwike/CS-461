package com.company;

import com.company.Courses.Courses;
import com.company.Instructors.Instructors;
import com.company.Rooms.Rooms;

public class Main {
    public static final String PATH = "data/";
    public static final String COURSE_DATA = "courses.txt";
    public static final String INSTRUCTOR_DATA = "instructors.txt";
    public static final String ROOM_DATA = "rooms.txt";

    public static final Courses COURSES = new Courses();
    public static final Instructors INSTRUCTORS = new Instructors();
    public static final Rooms ROOMS = new Rooms();

    public static void main(String[] args) {
        System.out.println(COURSES);
        System.out.println(INSTRUCTORS);
        System.out.println(ROOMS);
    }
}
