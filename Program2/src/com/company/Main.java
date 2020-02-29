package com.company;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private static final String PATH = "data/";
    private static final String COURSE_DATA = "courses.txt";
    private static final String FACULTY_DATA = "faculty.txt";
    private static final String ROOM_DATA = "rooms.txt";
    private static HashMap<String, Course> courses;
    private static HashMap<String, Instructor> faculty;
    private static HashMap<String, Room> rooms;

    public static void main(String[] args) {
        courses = new HashMap<>();
        faculty = new HashMap<>();
        rooms = new HashMap<>();

        try {
            initCourses();
            initFaculty();
            initRooms();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void initCourses() throws IOException {
        Scanner scanner = new Scanner(new FileInputStream(PATH + COURSE_DATA));

        while (scanner.hasNextLine()) {
            String dept = scanner.next();
            String name = scanner.next();
            int enrollment = scanner.nextInt();

            courses.put(name, new Course(dept, name, enrollment));
        }
    }

    private static void initFaculty() throws IOException {
        Scanner scanner = new Scanner(new FileInputStream(PATH + FACULTY_DATA));

        while (scanner.hasNextLine()) {
            String name = scanner.next();
            String dept = scanner.next();

            if (name.equals("Staff")) {
                faculty.put("Staff", new Staff(name, dept));
            } else {
                Adjunct adjunct = new Adjunct(name, dept);
                String[] courses = scanner
                        .skip(" ")
                        .nextLine()
                        .split(" ");
                adjunct.setCoursesCanTeach(courses);
                faculty.put(name, adjunct);
            }
        }
    }

    private static void initRooms() throws IOException {
        Scanner scanner = new Scanner(new FileInputStream(PATH + ROOM_DATA));

        while (scanner.hasNextLine()) {
            String building = scanner.next();
            String roomNum = scanner.next();
            int capacity = scanner.nextInt();
            String[] availableTimes = scanner
                    .skip(" ")
                    .nextLine()
                    .split(" ");
            String name = building + " " + roomNum;
            rooms.put(name, new Room(building, roomNum, capacity, availableTimes));
            System.out.println(rooms.get(name));
        }
    }
}