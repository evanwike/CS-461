package com.company;

import java.util.*;
import java.util.stream.Collectors;

public class Schedule {
    private static final Data data = Main.DATA;
    private List<Course> courses = data.getCourses();
    private List<Instructor> instructors = data.getInstructors();
    private List<Room> rooms = data.getRooms();
    private List<List<Course>> schedule = new ArrayList<>();
    private Random random = new Random();
    private int fitness = 0;

    // Initial random state
    public Schedule() {
        initSchedule();
        generateRandomSchedule();
        evaluateSchedule();
    }

    // Successor state
    public Schedule(Schedule parent) {
        this.schedule = parent.getSchedule();
        generateRandomSuccessor();
        evaluateSchedule();
    }

    private void initSchedule() {
        for (int i = 0; i < Main.nBLOCKS; i++)
            schedule.add(new ArrayList<>());
    }

    private void generateRandomSchedule() {
        for (Course course : courses) {
            int time = random.nextInt(schedule.size() - 1);
            Instructor instructor = instructors.get(random.nextInt(instructors.size() - 1));
            Room room = rooms.get(random.nextInt(rooms.size() - 1));

            course.setInstructor(instructor);
            course.setRoom(room);
            course.setTime(Main.START_TIME + time);

            schedule.get(time).add(course);
        }
    }

    private void generateRandomSuccessor() {
        // Select random attribute to change
        int attr = random.nextInt(3);
        int time = random.nextInt(schedule.size() - 1);

        // Ensure time block isn't empty
        while (schedule.get(time).size() == 0)
            time = random.nextInt(schedule.size() - 1);

        // Get random course in randomly selected time block
        List<Course> block = schedule.get(time);
        Course course = block.size() > 1 ? block.get(random.nextInt(block.size() - 1)) : block.get(0);

        // Change room
        if (attr == 0) {
            Room newRoom = getNewRandomRoom(course.getRoom());
            course.setRoom(newRoom);
        } // Change time
        else if (attr == 1) {
            int newTime = getNewRandomTime(time);
            course.setTime(Main.START_TIME + newTime);

            // Remove from old time block, add to new
            block.remove(course);
            schedule.get(newTime).add(course);
        } // Change both
        else {
            Room newRoom = getNewRandomRoom(course.getRoom());
            int newTime = getNewRandomTime(time);
            course.setRoom(newRoom);
            course.setTime(Main.START_TIME + newTime);

            // Remove from old time block, add to new
            block.remove(course);
            schedule.get(newTime).add(course);
        }
    }

    // Ensure room changes
    private Room getNewRandomRoom(Room room) {
        Room newRoom = rooms.get(random.nextInt(rooms.size() - 1));

        while (newRoom.equals(room))
            newRoom = rooms.get(random.nextInt(rooms.size() - 1));

        return newRoom;
    }

    // Ensure time changes
    private int getNewRandomTime(int time) {
        int newTime = random.nextInt(schedule.size() - 1);

        while (newTime == time)
            newTime = random.nextInt(schedule.size() - 1);

        return newTime;
    }

    public List<List<Course>> getSchedule() {
        List<List<Course>> outer = new ArrayList<>();

        for (List<Course> time : schedule) {
            List<Course> inner = time.stream()
                    .map(Course::copy)
                    .collect(Collectors.toList());
            outer.add(inner);
        }
        return outer;
    }

    private void evaluateSchedule() {

        // Time block conflicts
        for (List<Course> time : schedule) {
            for (Course course : time) {
                Instructor instructor = course.getInstructor();
                Room room = course.getRoom();

                if (instructor.isFaculty()) {
                    // Course taught by instructor who can teach it other than Staff
                    if (course.hasValidInstructor())
                        fitness += 3;
                } else {
                    // Taught by staff
                    fitness += 1;
                }

                // Only course scheduled in that room at that time
                if (!roomConflict(time, room))
                    fitness += 5;

                int enrollment = course.getEnrollment();
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

    private static boolean roomConflict(List<Course> time, Room room) {
        for (Course c : time)
            if (c.getRoom().equals(room))
                return true;
        return false;
    }

    private static boolean noInstructorConflict(List<Course> time, Instructor instructor) {
        int num = 0;

        for (Course c : time)
            if (c.getInstructor().equals(instructor))
                num++;

        return num == 1;
    }

    private Map<String, Integer> instructorCounts() {
        Map<String, Integer> counts = new HashMap<>();

        for (List<Course> time : schedule) {
            for (Course c : time) {
                Instructor instructor = c.getInstructor();
                counts.putIfAbsent(instructor.getName(), 1);
                counts.compute(instructor.getName(), (k, v) -> v + 1);
            }
        }

        return counts;
    }

    private boolean takenTogetherConflict(String courseNum1, String courseNum2, List<Course> time) {
        List<Course> first = filterByCourseNumber(courseNum1);
        List<Course> second = filterByCourseNumber(courseNum2);

        // If any section of either course in same time block
        for (Course c1 : first)
            for (Course c2 : second)
                if (time.contains(c1) && time.contains(c2))
                    return true;

        return false;
    }

    private int courseAdjacencyFitness(String courseNum1, String courseNum2) {
        int fitness = 0;
        List<Course> classes1 = filterByCourseNumber(courseNum1);
        List<Course> classes2 = filterByCourseNumber(courseNum2);

        for (int i = 0; i < schedule.size(); i++) {
            List<Course> current = schedule.get(i);
            List<Course> adjacent;

            if (i == 0) {
                // Time block after first
                adjacent = new ArrayList<>(schedule.get(1));
            } else if (i == schedule.size() - 1) {
                // Time block before last
                adjacent = new ArrayList<>(schedule.get(i - 2));
            } else {
                // Time blocks before and after current
                adjacent = new ArrayList<>(schedule.get(i - 1));
                adjacent.addAll(schedule.get(i + 1));
            }

            for (Course c1 : classes1) {
                for (Course c2 : classes2) {
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
        Course c1 = courses.stream()
                .filter(c -> c.getName().contains(courseNum1))
                .collect(Collectors.toList()).get(0);
        Course c2 = courses.stream()
                .filter(c -> c.getName().contains(courseNum2))
                .collect(Collectors.toList()).get(0);
        int index1 = -1;
        int index2 = -1;

        for (List<Course> time : schedule) {
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
    
    private List<Course> filterByCourseNumber(String courseNum) {
        return courses.stream()
                .filter(c -> c.getName().contains(courseNum))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fitness: ").append(fitness).append('\n');

        for (List<Course> time : schedule)
            for (Course c : time)
                sb.append(c.out()).append('\n');

        return sb.toString();
    }
}
