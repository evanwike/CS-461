package com.company;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Main {
    private static final String PATH = "data/";
    private static final String COURSE_DATA = "courses.txt";
    private static final String INSTRUCTOR_DATA = "instructors.txt";
    private static final String ROOM_DATA = "rooms.txt";
    public static List<Course> COURSES;
    private static List<Instructor> INSTRUCTORS;
    private static List<Room> ROOMS;

    public static void main(String[] args) {
        COURSES = new ArrayList<>();
        INSTRUCTORS = new ArrayList<>();
        ROOMS = new ArrayList<>();

        try {
            initCourses();
            initInstructors();
            initRooms();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void initCourses() throws IOException {
        Scanner scanner = new Scanner(new FileInputStream(PATH + COURSE_DATA));

        while (scanner.hasNextLine()) {
            String dept = scanner.next();
            String courseNum = scanner.next();
            int enrollment = scanner.nextInt();

            COURSES.add(new Course(dept, courseNum, enrollment));
        }
    }

    private static void initInstructors() throws IOException {
        Scanner scanner = new Scanner(new FileInputStream(PATH + INSTRUCTOR_DATA));

        while (scanner.hasNextLine()) {
            Instructor instructor;
            String name = scanner.next();
            String dept = scanner.next();

            if (name.equals("Staff")) {
                instructor = new Staff(name, dept);
            } else {
                String[] courseNums = scanner
                        .skip(" ")
                        .nextLine()
                        .split(" ");
                instructor = new Faculty(name, dept, courseNums);
            }

            INSTRUCTORS.add(instructor);
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
            ROOMS.add(new Room(building, roomNum, capacity, availableTimes));
            rooms.put(name, new Room(building, roomNum, capacity, availableTimes));
            System.out.println(rooms.get(name));
        }
    }
}

public class Main {
    private static final String PATH = "data/";
    private static final String COURSE_DATA = "courses.txt";
    private static final String INSTRUCTOR_DATA = "instructors.txt";
    private static final String ROOM_DATA = "rooms.txt";
    public static HashMap<String, Course> courses;
    private static HashMap<String, Instructor> instructors;
    private static HashMap<String, Room> rooms;

    public static void main(String[] args) {
        courses = new HashMap<>();
        instructors = new HashMap<>();
        rooms = new HashMap<>();

        try {
            initCourses();
            initInstructors();
            initRooms();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // Create new course objects for each input
    private static void initCourses() throws IOException {
        Scanner scanner = new Scanner(new FileInputStream(PATH + COURSE_DATA));

        while (scanner.hasNextLine()) {
            String dept = scanner.next();
            String courseNum = scanner.next();
            int enrollment = scanner.nextInt();

            courses.put(courseNum, new Course(dept, courseNum, enrollment));
        }
    }

    private static void initInstructors() throws IOException {
        Scanner scanner = new Scanner(new FileInputStream(PATH + INSTRUCTOR_DATA));

        while (scanner.hasNextLine()) {
            String name = scanner.next();
            String dept = scanner.next();

            if (name.equals("Staff")) {
                instructors.put("Staff", new Staff(name, dept));
            } else {
                String[] courseNums = scanner
                        .skip(" ")
                        .nextLine()
                        .split(" ");
                instructors.put(name, new Faculty(name, dept, courses));
            }
        }
    }

    private HashSet<Course> getCourses(String[] courseNums) {
        Set<Course> matched = new HashSet<>();

        courses.forEach();
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