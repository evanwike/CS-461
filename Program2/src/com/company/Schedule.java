package com.company;

import com.company.Classes.Class;
import com.company.Classes.Classes;
import com.company.Courses.Course;
import com.company.Instructors.Instructor;
import com.company.Rooms.Room;

import java.util.*;


public class Schedule {
    private static final int BLOCKS = 7;
    private List<Class>[] schedule;
    private int fitness;
    Classes classes;

    public Schedule() {
        this.schedule = new ArrayList[BLOCKS];
        this.fitness = 0;
        this.classes = new Classes();


        initSchedule();
        generateRandomSchedule();
        evaluateSchedule();
    }

    private void initSchedule() {
        for (int i = 0; i < BLOCKS; i++)
            schedule[i] = new ArrayList<>();
    }

    private void generateRandomSchedule() {
        for (Class randomClass : classes.getRandomClassList()) {
            int randInt = new Random().nextInt(BLOCKS);
            schedule[randInt].add(randomClass);
            randomClass.setTime(10 + randInt);
        }
    }

    private void evaluateSchedule() {

        // Time block conflicts
        for (List<Class> time : schedule) {
            for (Class c : time) {
                Course course = c.getCourse();
                Instructor instructor = c.getInstructor();
                Room room = c.getRoom();

                if (instructor.isFaculty()) {
                    // Course taught by instructor who can teach it other than Staff
                    if (instructor.canTeach(course.getCourseNum()))
                        fitness += 3;
                } else {
                    // Taught by staff
                    fitness += 1;
                }

                // Only course scheduled in that room at that time
                if (noRoomConflict(time, room))
                    fitness += 5;
                
                int enrollment = course.getExpEnrollment();
                int capacity = room.getCapacity();

                // In room large enough to accommodate it
                if (enrollment < capacity)
                    fitness += 5;
                
                // Room capacity no more than twice the expected enrollment
                if (capacity <= (enrollment * 2))
                    fitness += 2;

                // Instructor only teaching one course in each time
                if (noInstructorConflict(time, instructor))
                    fitness += 5;

                // Courses usually taken together not at same time
                if (takenTogetherConflict("101", "191", time))
                    fitness -= 15;
                if (takenTogetherConflict("201", "291", time))
                    fitness -= 15;
            }
        }

        Map<String, Integer> counts = instructorCounts();

        // Same instructor teaching more than 4 courses (-5 for each course over 4)
        for (int count : counts.values())
            if (count > 4)
                fitness -= (count - 4) * 5;

        // Rao or Mitchell teaching more courses than Hare or Bingham
        int Rao = counts.getOrDefault("Rao", 0);
        int Mitchell = counts.getOrDefault("Mitchell", 0);
        int Hare = counts.getOrDefault("Hare", 0);
        int Bingham = counts.getOrDefault("Bingham", 0);

        if (Rao > Hare || Rao > Bingham)
            fitness -= 10;

        if (Mitchell > Hare || Mitchell > Bingham)
            fitness -= 10;

        // Course adjacency
        fitness += courseAdjacencyFitness("101", "191");
        fitness += courseAdjacencyFitness("201", "291");

        // Three hours apart
        if (threeHoursApart("101A", "101B"))
            fitness += 5;
        if (threeHoursApart("191A", "191B"))
            fitness += 5;

    }

    private static boolean noRoomConflict(List<Class> time, Room room) {
        int num = 0;

        for (Class c : time)
            if (c.getRoom().equals(room))
                num++;

        return num == 1;
    }
    
    private static boolean noInstructorConflict(List<Class> time, Instructor instructor) {
        int num = 0;
        
        for (Class c : time)
            if (c.getInstructor().equals(instructor))
                num++;
            
        return num == 1;    
    }

    private Map<String, Integer> instructorCounts() {
        Map<String, Integer> counts = new HashMap<>();

        for (List<Class> time : schedule) {
            for (Class c : time) {
                Instructor instructor = c.getInstructor();
                counts.putIfAbsent(instructor.getName(), 1);
                counts.compute(instructor.getName(), (k, v) -> v + 1);
            }
        }

        return counts;
    }

    private boolean takenTogetherConflict(String courseNum1, String courseNum2, List<Class> time) {
        List<Class> classes1 = classes.getClassesByCourseNumber(courseNum1);
        List<Class> classes2 = classes.getClassesByCourseNumber(courseNum2);

        // If any section of either course in same time block
        for (Class c1 : classes1)
            for (Class c2 : classes2)
                if (time.contains(c1) && time.contains(c2))
                    return true;

        return false;
    }

    private int courseAdjacencyFitness(String courseNum1, String courseNum2) {
        int fitness = 0;
        List<Class> classes1 = classes.getClassesByCourseNumber(courseNum1);
        List<Class> classes2 = classes.getClassesByCourseNumber(courseNum2);

        for (int i = 0; i < schedule.length; i++) {
            List<Class> current = schedule[i];
            List<Class> adjacent;

            if (i == 0) {
                // Time block after first
                adjacent = new ArrayList<>(schedule[1]);
            } else if (i == schedule.length - 1) {
                // Time block before last
                adjacent = new ArrayList<>(schedule[i-2]);
            } else {
                // Time blocks before and after current
                adjacent = new ArrayList<>(schedule[i-1]);
                adjacent.addAll(schedule[i + 1]);
            }

            for (Class c1 : classes1) {
                for (Class c2 : classes2) {
                    // Courses scheduled for adjacent times
                    if (current.contains(c1) && adjacent.contains(c2)) {
                        fitness += 5;

                        String b1 = c1.getRoom().getBuilding();
                        String b2 = c2.getRoom().getBuilding();

                        // And are in the same building
                        if (b1.equals(b2))
                            fitness += 5;

                        // One in Katz, other isn't
                        if ((b1.equals("Katz") && !b2.equals("Katz")) || (!b1.equals("Katz") && b2.equals("Katz")))
                            fitness -= 3;

                        // One in Block, other isn't
                        if ((b1.equals("Bloch") && !b2.equals("Bloch")) || (!b1.equals("Bloch") && b2.equals("Bloch")))
                            fitness -= 3;

                        // One in Katz, other in Bloch
                        if ((b1.equals("Katz") && b2.equals("Bloch")) || (b1.equals("Bloch") && b2.equals("Katz")))
                            fitness -= 6;
                    }
                }
            }
        }
        return fitness;
    }

    private boolean threeHoursApart(String courseNum1, String courseNum2) {
        Class c1 = classes.getClassByCourseNumber(courseNum1);
        Class c2 = classes.getClassByCourseNumber(courseNum2);
        int index1 = -1;
        int index2 = -1;

        for (List<Class> time : schedule) {
            if (time.contains(c1))
                index1 = time.indexOf(c1);
            if (time.contains(c2))
                index2 = time.indexOf(c2);
        }

        return Math.abs(index1 - index2) >= 3;
    }

    public int getFitness() {
        return fitness;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fitness: ").append(fitness).append('\n');
        for (List<Class> block : schedule)
            for (Class c : block)
                sb.append(c).append('\n');

        return sb.toString();
    }
}
