package com.company;

public class Room {
    private String building;
    private String room;
    private int capacity;

    public Room(String building, String room, int capacity) {
        this.building = building;
        this.room = room;
        this.capacity = capacity;
    }

    public String getBuilding() {
        return building;
    }

    public String getRoom() {
        return room;
    }

    public int getCapacity() {
        return capacity;
    }

    public Room copy() {
        return new Room(building, room, capacity);
    }

    public String out() {
        return String.format("%s %s", building, room);
    }

    @Override
    public String toString() {
        return "Room{" +
                "building='" + building + '\'' +
                ", room='" + room + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}
