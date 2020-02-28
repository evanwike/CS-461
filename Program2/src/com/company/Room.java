package com.company;

import java.util.Arrays;
import java.util.List;

public class Room {
    private String building;
    private String roomNum;
    private int capacity;
    private List<String> availableTimes;

    public Room(String building, String roomNum, int capacity, String[] availableTimes) {
        this.building = building;
        this.roomNum = roomNum;
        this.capacity = capacity;
        this.availableTimes = Arrays.asList(availableTimes);
    }

    public String getBuilding() {
        return building;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<String> getAvailableTimes() { return availableTimes; }

    @Override
    public String toString() {
        return "Room{" +
                "building='" + building + '\'' +
                ", roomNum='" + roomNum + '\'' +
                ", capacity=" + capacity +
                ", availableTimes=" + availableTimes +
                '}';
    }
}
