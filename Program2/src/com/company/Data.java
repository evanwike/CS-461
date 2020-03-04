package com.company;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Data {
    private List<Course> courses;
    private List<Instructor> instructors;
    private List<Room> rooms;

    public Data() {
        courses = new ArrayList<>();
        instructors = new ArrayList<>();
        rooms = new ArrayList<>();

        try {
            processCourseData();
            processInstructorData();
            processRoomData();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    private void processCourseData() throws IOException {
        Scanner scanner = new Scanner(new FileInputStream(Main.PATH + Main.COURSE_DATA));

        while (scanner.hasNextLine()) {
            String dept = scanner.next();
            String courseNum = scanner.next();
            String name = dept + ' ' + courseNum;
            int expEnrollment = scanner.nextInt();

            courses.add(new Course(name, expEnrollment));
        }
    }

    private void processInstructorData() throws IOException {
        Scanner scanner = new Scanner(new FileInputStream(Main.PATH + Main.INSTRUCTOR_DATA));

        while (scanner.hasNextLine()) {
            String name = scanner.next();
            String dept = scanner.next();
            List<Course> coursesCanTeach;

            if (name.equals("Staff")) {
                // Staff can teach all courses
                coursesCanTeach = new ArrayList<>(courses);
            } else {
                coursesCanTeach = new ArrayList<>();
                // Get course numbers from input as list of strings
                // Get all courses containing that course number and add them to coursesCanTeach list
                Arrays.asList(scanner
                        .skip(" ")
                        .nextLine()
                        .split(" "))
                        .forEach(n ->
                            coursesCanTeach.addAll(
                                    courses.stream()
                                            .filter(course -> course.getName().contains(n))
                                            .collect(Collectors.toList())));
            }

            instructors.add(new Instructor(name, dept, coursesCanTeach));
        }
    }

    private void processRoomData() throws IOException {
        Scanner scanner = new Scanner(new FileInputStream(Main.PATH + Main.ROOM_DATA));

        while (scanner.hasNextLine()) {
            String building = scanner.next();
            String roomNum = scanner.next();
            int capacity = scanner.nextInt();

            rooms.add(new Room(building, roomNum, capacity));
        }
    }

    public List<Course> getCourses() {
        return List.copyOf(courses);
    }

    public List<Instructor> getInstructors() {
        return List.copyOf(instructors);
    }

    public List<Room> getRooms() {
        return List.copyOf(rooms);
    }
}


