package com.company.Classes;

import com.company.Courses.Course;
import com.company.Instructors.Instructor;
import com.company.Main;
import com.company.Rooms.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Classes {
    private List<Course> courses;
    private List<Instructor> instructors;
    private List<Room> rooms;
    private List<Class> randomClassList;

    public Classes() {
        courses = Main.COURSES.getCourses();
        instructors = Main.INSTRUCTORS.getInstructors();
        rooms = Main.ROOMS.getRooms();

        setRandomClassList();
    }

    private void setRandomClassList() {
        List<Class> random = new ArrayList<>();

        for (Course course : courses) {
            Instructor instructor = getRandomInstructor();
            Room room = getRandomRoom();
            Class randomClass = new Class(course, instructor, room);

            random.add(randomClass);
        }

        randomClassList = random;
    }

    private Instructor getRandomInstructor() {
        int randInt = new Random().nextInt(instructors.size());
        return instructors.get(randInt);
    }

    private Room getRandomRoom() {
        int randInt = new Random().nextInt(rooms.size());
        return rooms.get(randInt);
    }

    public List<Class> getRandomClassList() {
        return randomClassList;
    }

    public Class getClassByCourseNumber(String courseNum) {
        for (Class c : randomClassList)
            if (c.getCourse().getCourseNum().equals(courseNum))
                return c;
        return null;
    }

    public List<Class> getClassesByCourseNumber(String courseNum) {
        List<Class> matching = new ArrayList<>();

        for (Class c : randomClassList)
            if (c.getCourse().getCourseNum().contains(courseNum))
                matching.add(c);

        return matching;
    }
}
