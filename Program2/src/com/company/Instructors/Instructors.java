package com.company.Instructors;

import com.company.Main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Instructors {
    private List<Instructor> instructors;

    public Instructors() {
        this.instructors = new ArrayList<>();

        try {
            processInstructorData();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void processInstructorData() throws IOException {
        Scanner scanner = new Scanner(new FileInputStream(Main.PATH + Main.INSTRUCTOR_DATA));
        Instructor instructor;

        while (scanner.hasNextLine()) {
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

            instructors.add(instructor);
        }
    }

    public List<Instructor> getInstructors() {
        List<Instructor> copy = new ArrayList<>();
        instructors.forEach(instructor -> copy.add(instructor.getCopy()));
        return copy;
    }

    @Override
    public String toString() {
        return "Instructors{" +
                "instructors=" + instructors +
                '}';
    }
}
